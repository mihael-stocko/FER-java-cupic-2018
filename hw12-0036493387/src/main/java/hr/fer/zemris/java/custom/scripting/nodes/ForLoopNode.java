package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * A representation of a for loop node.
 * 
 * @author Mihael Stočko
 *
 */
public class ForLoopNode extends Node {
	
	/**
	 * Variable.
	 */
	private ElementVariable variable;
	
	/**
	 * Start expression.
	 */
	private Element startExpression;
	
	/**
	 * End expression.
	 */
	private Element endExpression;
	
	/**
	 * Step expression. Optional.
	 */
	private Element stepExpression;
	
	/**
	 * Constructor.
	 * 
	 * @param variable Variable.
	 * @param startExpression Start expression.
	 * @param endExpression End expression.
	 * @param stepExpression Step expression. Can be null.
	 * @throws NullPointerException
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		if(variable == null) {
			throw new NullPointerException("variable must not be null.");
		}
		if(startExpression == null) {
			throw new NullPointerException("startExpression must not be null.");
		}
		if(endExpression == null) {
			throw new NullPointerException("endExpression must not be null.");
		}
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter for variable.
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for startExpression.
	 * 
	 * @return startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for endExpression.
	 * 
	 * @return endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for stepExpression.
	 * 
	 * @return stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Calls the given INodeVisitor's visitForLoopNode method with itself as an argument.
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
