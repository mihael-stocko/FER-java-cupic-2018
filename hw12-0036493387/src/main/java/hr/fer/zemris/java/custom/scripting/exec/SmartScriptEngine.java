package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This is an engine for executing a parsed SmartScript. The results of exeuction are
 * sent to the provided RequestContext.
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptEngine {
	/**
	 * Multistack key for storing values.
	 */
	private static String forStackKey = "forKey";
	
	/**
	 * Document node of the SmartScript
	 */
	private DocumentNode documentNode;
	
	/**
	 * Request context for outputting results
	 */
	private RequestContext requestContext;
	
	/**
	 * Multistack used for storing temporary results
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Visitor that traverses the document tree
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0, numOfChildren = node.numberOfChildren(); i < numOfChildren; i++) {
				node.getChild(i).accept(this);
			}
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();
			for(Element e : node.getElements()) {
				if(e instanceof ElementConstantInteger) {
					stack.push(new ValueWrapper(((ElementConstantInteger)e).getValue()));
				} else if(e instanceof ElementConstantDouble) {
					stack.push(new ValueWrapper(((ElementConstantDouble)e).getValue()));
				} else if(e instanceof ElementString) {
					stack.push(((ElementString)e).getValue());
				} else if(e instanceof ElementVariable) {
					stack.push(new ValueWrapper(multistack.peek(((ElementVariable)e).getName()).getValue()));
				} else if(e instanceof ElementOperator) {
					Object o1 = stack.pop();
					Object o2 = stack.pop();
					if(!(o1 instanceof ValueWrapper || o1 instanceof String) || 
							!(o2 instanceof ValueWrapper || o2 instanceof String)) {
						throw new IllegalArgumentException("Invalid operation argument type.");
					}
					
					ValueWrapper operand1;
					ValueWrapper operand2;
					if(o1 instanceof String) {
						operand1 = new ValueWrapper(Double.parseDouble((String)o1));
					} else {
						operand1 = (ValueWrapper)o1;
					}
					if(o2 instanceof String) {
						operand2 = new ValueWrapper(Double.parseDouble((String)o2));
					} else {
						operand2 = (ValueWrapper)o2;
					}
					
					String operator = ((ElementOperator)e).getSymbol();
					switch(operator) {
					case "+":
						operand1.add(operand2.getValue());
						break;
					case "-":
						operand1.subtract(operand2.getValue());
						break;
					case "*":
						operand1.multiply(operand2.getValue());
						break;
					case "/":
						operand1.divide(operand2.getValue());
						break;
					default:
						throw new UnsupportedOperationException("Unsupported arithmetic operation: " + operator);
					}
					
					stack.push(operand1);
				} else if(e instanceof ElementFunction) {
					callFunction(stack, (ElementFunction)e);
				} else {
					throw new IllegalArgumentException("Illegal argument for echo.");
				}	
			}
			
			List<Object> list = new ArrayList<>();
			while(true) {
				if(stack.isEmpty()) {
					break;
				}
				list.add(0, stack.pop());
			}
			for(Object o : list) {
				try {
					if(o instanceof String) {
						try {
							requestContext.write(String.format("%.0f", Double.parseDouble((String)o)));
						} catch(NumberFormatException e) {
							requestContext.write((String)o);
						}
					} else if(((ValueWrapper)o).getValue() instanceof Double) {
						requestContext.write(String.format("%.0f", ((ValueWrapper)o).getValue()));
					} else {
						requestContext.write(((ValueWrapper)o).getValue().toString());
					}
					
				} catch(IOException e) {
					throw new IllegalStateException("Error while writing. " + e.getMessage());
				}
			}
		}
		
		/**
		 * Called when a function is to be applied
		 * 
		 * @param stack Multistack with values
		 * @param e Function to be performed
		 */
		private void callFunction(Stack<Object> stack, ElementFunction e) {
			String func = e.getName();
			switch(func) {
			case "sin":
				stack.push(sin(stack.pop()));
				break;
			case "decfmt":
				stack.push(decfmt(stack.pop(), stack.pop()));
				break;
			case "dup":
				Object obj = stack.pop();
				stack.push(obj);
				stack.push(obj);
				break;
			case "swap":
				Object obj1 = stack.pop();
				Object obj2 = stack.pop();
				stack.push(obj1);
				stack.push(obj2);
				break;
			case "setMimeType":
				setMimeType(stack.pop());
				break;
			case "paramGet":
				stack.push(paramGet(stack.pop(), stack.pop()));
				break;
			case "pparamGet":
				stack.push(pparamGet(stack.pop(), stack.pop()));
				break;
			case "pparamSet":
				pparamSet(stack.pop(), stack.pop());
				break;
			case "pparamDel":
				pparamDel(stack.pop());
				break;
			case "tparamGet":
				stack.push(tparamGet(stack.pop(), stack.pop()));
				break;
			case "tparamSet":
				tparamSet(stack.pop(), stack.pop());
				break;
			case "tparamDel":
				tparamDel(stack.pop());
				break;
			default:
				throw new UnsupportedOperationException("Unsupported function: " + func + ".");
			}
		}
		
		/**
		 * Calculates sine of the given object. The object must be a number.
		 * 
		 * @param o Parameter for sine
		 * @return Wrapped result
		 */
		private ValueWrapper sin(Object o) {
			if(!(o instanceof ValueWrapper)) {
				throw new IllegalArgumentException("Argument for sin must be of type ValueWrapper.");
			}
			
			Object argument = ((ValueWrapper)o).getValue();
			
			double arg;
			if(argument instanceof Integer) {
				arg = ((Integer)argument)/360.0*Math.PI*2;
			} else {
				arg = ((Double)argument)/360*Math.PI*2;
			}
			return new ValueWrapper(Math.sin(arg));
		}
		
		/**
		 * Formats the given number using the given string
		 * 
		 * @param o2 string
		 * @param o1 number
		 * @return Formatted number as a String
		 */
		private String decfmt(Object o2, Object o1) {
			if(!(o1 instanceof ValueWrapper)) {
				throw new IllegalArgumentException("First argument for decfmt must be of type ValueWrapper.");
			}
			if(!(o2 instanceof String)) {
				throw new IllegalArgumentException("Second argument for decfmt must be of type String.");
			}
			
			Object o = ((ValueWrapper)o1).getValue();
			if(!(o instanceof Double)) {
				throw new IllegalArgumentException("First argument for decfmt must be a decimal number "
						+ "wrapped in ValueWrapper.");
			}
			
			NumberFormat formatter = new DecimalFormat((String)o2);
			return formatter.format(((ValueWrapper)o1).getValue());
		}
		
		/**
		 * Sets the mimeType to the one given
		 */
		private void setMimeType(Object x) {
			if(!(x instanceof String)) {
				throw new IllegalArgumentException("Argument for setMimeType must be of type String.");
			}
			
			requestContext.setMimeType((String)x);
		}
		
		/**
		 * Gets the requested parameter from the parameters map. If the requested object
		 * does not exist in the map, returns the provided default value.
		 */
		private String paramGet(Object defValue, Object name) {
			if(!(name instanceof String)) {
				throw new IllegalArgumentException("Second argument for paramGet must be of type String.");
			}
			
			String value = requestContext.getParameter((String)name);
			return value == null ? defValue.toString() : value;
		}
		
		/**
		 * Gets the requested parameter from the permanent parameters map. If the requested object
		 * does not exist in the map, returns the provided default value.
		 */
		private String pparamGet(Object defValue, Object name) {
			if(!(name instanceof String)) {
				throw new IllegalArgumentException("Second argument for pparamGet must be of type String.");
			}
			
			String value = requestContext.getPersistentParameter((String)name);
			return value == null ? defValue.toString() : value;
		}
		
		/**
		 * Sets the value of the object mapped by the given name in the permanent parameters map
		 * to the value given.
		 */
		private void pparamSet(Object name, Object value) {
			if(!(name instanceof String)) {
				throw new IllegalArgumentException("Second argument for pparamSet must be of type String.");
			}
			
			if(value instanceof String) {
				requestContext.setPersistentParameter((String)name, (String)value);
			} else if(value instanceof ValueWrapper) {
				requestContext.setPersistentParameter((String)name, ((ValueWrapper)value).getValue().toString());
			} else {
				throw new IllegalArgumentException("Argument value for pparamSet must be of type "
						+ "String or ValueWrapper.");
			}
		}
		
		/**
		 * Deletes from the permanent parameters the value mapped with the given name.
		 * 
		 * @param name Key of the object to be deleted.
		 */
		private void pparamDel(Object name) {
			if(!(name instanceof String)) {
				throw new IllegalArgumentException("Argument for pparamDel must be of type String.");
			}
			
			requestContext.removePersistentParameter((String)name);
		}
		
		/**
		 * Sets the value of the object mapped by the given name in the temporary parameters map
		 * to the value given.
		 */
		private void tparamSet(Object name, Object value) {
			if(!(name instanceof String)) {
				throw new IllegalArgumentException("Second argument for tparamSet must be of type String.");
			}
			
			if(value instanceof String) {
				requestContext.setTemporaryParameter((String)name, (String)value);
			} else if(value instanceof ValueWrapper) {
				requestContext.setTemporaryParameter((String)name, ((ValueWrapper)value).getValue().toString());
			} else {
				throw new IllegalArgumentException("Argument value for tparamSet must be of type "
						+ "String or ValueWrapper.");
			}
		}
		
		/**
		 * Gets the requested parameter from the temporary parameters map. If the requested object
		 * does not exist in the map, returns the provided default value.
		 */
		private String tparamGet(Object defValue, Object name) {
			if(!(name instanceof String)) {
				throw new IllegalArgumentException("Second argument for tparamGet must be of type String.");
			}
			
			String value = requestContext.getTemporaryParameter((String)name);
			return value == null ? defValue.toString() : value;
		}
		
		/**
		 * Deletes from the temporary parameters the value mapped with the given name.
		 * 
		 * @param name Key of the object to be deleted.
		 */
		private void tparamDel(Object name) {
			if(!(name instanceof String)) {
				throw new IllegalArgumentException("Argument for tparamDel must be of type String.");
			}
			
			requestContext.removeTemporaryParameter((String)name);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			int start;
			int end;
			int step;
			
			Element startElem = node.getStartExpression();
			if(startElem instanceof ElementConstantInteger) {
				start = ((ElementConstantInteger)startElem).getValue();
			} else if(startElem instanceof ElementString) {
				try {
					start = Integer.parseInt(((ElementString)startElem).getValue());
				} catch(NumberFormatException e) {
					throw new IllegalArgumentException("Node contains illegal string format. " + e.getMessage());
				}
			} else if(startElem instanceof ElementVariable) {
				start = (Integer)(multistack.peek(((ElementVariable)startElem).getName()).getValue());
			} else {
				throw new IllegalArgumentException("Starting element is not in a required format.");
			}
			
			Element endElem = node.getEndExpression();
			if(endElem instanceof ElementConstantInteger) {
				end = ((ElementConstantInteger)endElem).getValue();
			} else if(endElem instanceof ElementString) {
				try {
					end = Integer.parseInt(((ElementString)endElem).getValue());
				} catch(NumberFormatException e) {
					throw new IllegalArgumentException("Node contains illegal string format. " + e.getMessage());
				}
			} else if(endElem instanceof ElementVariable) {
				end = (Integer)(multistack.peek(((ElementVariable)endElem).getName()).getValue());
			} else {
				throw new IllegalArgumentException("Ending element is not in a required format.");
			}
			
			Element stepElem = node.getStepExpression();
			if (stepElem == null) {
				step = 1;
			} else if(stepElem instanceof ElementConstantInteger) {
				step = ((ElementConstantInteger)stepElem).getValue();
			} else if(stepElem instanceof ElementString) {
				try {
					step = Integer.parseInt(((ElementString)stepElem).getValue());
				} catch(NumberFormatException e) {
					throw new IllegalArgumentException("Node contains illegal string format. " + e.getMessage());
				}
			} else if(stepElem instanceof ElementVariable) {
				step = (Integer)(multistack.peek(((ElementVariable)stepElem).getName()).getValue());
			} else {
				throw new IllegalArgumentException("Ending element is not in a required format.");
			}
			
			multistack.push(forStackKey, new ValueWrapper(node.getVariable()));
			multistack.push(node.getVariable().getName(), new ValueWrapper(start));
			while(true) {
				for(int i = 0, numOfChildren = node.numberOfChildren(); i < numOfChildren; i++) {
					node.getChild(i).accept(this);
				}
				
				int currentValue = (Integer)(multistack.pop(node.getVariable().getName()).getValue());
				currentValue += step;
				if(currentValue > end) {
					break;
				}
				multistack.push(node.getVariable().getName(), new ValueWrapper(currentValue));
			}
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch(IOException e) {
				throw new IllegalStateException("Error while writing. " + e.getMessage());
			}
		}
	};
	
	/**
	 * Constructor.
	 * 
	 * @param documentNode Document node of the SmartScript tree.
	 * @param requestContext Requets context for outputting the results.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode, "Document node cannot be null.");
		Objects.requireNonNull(requestContext, "Request context cannot be null.");
		
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Executes the script.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
