package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * A simple parser. Reads a string and parses it into a tree.
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptParser {
	
	/**
	 * An ObjectStack used internally.
	 */
	private ObjectStack stack;
	
	/**
	 * Document to be parsed.
	 */
	private String document;
	
	/**
	 * The mode that the parser is in.
	 */
	private ParserMode mode;
	
	/**
	 * Root of the tree.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Constructor. Takes a string for parsing and parses it.
	 * 
	 * @param string
	 */
	public SmartScriptParser(String string) {
		if(string == null) {
			throw new NullPointerException("Parser does not accept null as the argument.");
		}
		document = string;
		stack = new ObjectStack();
		mode = ParserMode.TEXT;
		parse();
	}
	
	/**
	 * Getter for the root of the tree.
	 * 
	 * @return documentNode
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * The method parses the document into the documentNode
	 */
	public void parse() {
		 SmartScriptLexer lexer = new SmartScriptLexer(document);
		 DocumentNode docNode = new DocumentNode();
		 stack.push(docNode);
		 
		 while(true) {
			 try {
				 SmartScriptToken token = lexer.nextToken();
				 if(token.getType() == SmartScriptTokenType.EOF) {
					 break;
				 }
				 
				 if(mode == ParserMode.TEXT) {
					 if(token.getType() == SmartScriptTokenType.STRING) {
						 Node node = (Node)stack.peek();
						 node.addChildNode(new TextNode(token.getValue().toString()));
					 } else if(token.getType() == SmartScriptTokenType.TAGSTART) {
						 lexer.setState(SmartScriptLexerState.TAG);
						 token = lexer.nextToken();
						 if(token.getType() == SmartScriptTokenType.VARIABLE) {
							 if(token.getValue().toString().toUpperCase().equals("FOR")) {
								 mode = ParserMode.FOR;
							 } else if(token.getValue().toString().toUpperCase().equals("END")) {
								 mode = ParserMode.END;
							 } else {
								 throw new SmartScriptParserException("Syntax error.");
							 }
						 } else if(token.getType() == SmartScriptTokenType.OPERATOR) {
							 if(token.getValue().toString().equals("=")) {
								 mode = ParserMode.ECHO;
							 } else {
								 throw new SmartScriptParserException("Syntax error.");
							 }
						 } else {
							 throw new SmartScriptParserException("Syntax error.");
						 }
					 } else {
						 throw new SmartScriptParserException("Syntax error.");
					 }
				 
				 } else if(mode == ParserMode.ECHO) {
					 ArrayIndexedCollection collection = new ArrayIndexedCollection();
					 while(true) {
						 if(token.getType() == SmartScriptTokenType.DOUBLE) {
							 collection.add(new ElementConstantDouble((Double)token.getValue()));
						 } else if(token.getType() == SmartScriptTokenType.INTEGER) {
							 collection.add(new ElementConstantInteger((Integer)token.getValue()));
						 } else if(token.getType() == SmartScriptTokenType.STRING) {
							 collection.add(new ElementString((String)token.getValue()));
						 } else if(token.getType() == SmartScriptTokenType.OPERATOR) {
							 collection.add(new ElementOperator(token.getValue().toString()));
						 } else if(token.getType() == SmartScriptTokenType.FUNCTION) {
							 collection.add(new ElementFunction((String)token.getValue()));
						 } else if(token.getType() == SmartScriptTokenType.VARIABLE) {
							 collection.add(new ElementVariable((String)token.getValue()));
						 } else if(token.getType() == SmartScriptTokenType.TAGEND) {
							 Element[] elements = new Element[collection.size()];
							 for(int i = 0, colSize = collection.size(); i < colSize; ++i) {
								 elements[i] = (Element)collection.get(i);
							 }
							 EchoNode echoNode = new EchoNode(elements);
							 Node node = (Node)stack.peek();
							 node.addChildNode(echoNode);
							 lexer.setState(SmartScriptLexerState.TEXT);
							 mode = ParserMode.TEXT;
							 break;
						 } else {
							 throw new SmartScriptParserException("Syntax error.");
						 }
						 token = lexer.nextToken();
					 }
				 
				 } else if(mode == ParserMode.FOR) {
					 ElementVariable var;
					 Element[] param = new Element[3];
					 if(token.getType() == SmartScriptTokenType.VARIABLE) {
						 var = new ElementVariable(token.getValue().toString());
					 } else {
						 throw new SmartScriptParserException("Syntax error.");
					 }
					 for(int i = 0; i < 2; i++) {
						 token = lexer.nextToken();
						 if(token.getType() == SmartScriptTokenType.INTEGER) {
							 param[i] = new ElementConstantInteger((Integer)token.getValue());
						 } else if(token.getType() == SmartScriptTokenType.STRING) {
							 param[i] = new ElementString(token.getValue().toString());
						 } else if(token.getType() == SmartScriptTokenType.VARIABLE) {
							 param[i] = new ElementVariable(token.getValue().toString());
						 } else {
							 throw new SmartScriptParserException("Syntax error.");
						 }
					 }
					 token = lexer.nextToken();
					 
					 if(token.getType() == SmartScriptTokenType.INTEGER) {
						 param[2] = new ElementConstantInteger((Integer)token.getValue());
						 token = lexer.nextToken();
					 } else if(token.getType() == SmartScriptTokenType.STRING) {
						 param[2] = new ElementString(token.getValue().toString());
						 token = lexer.nextToken();
					 } else if(token.getType() == SmartScriptTokenType.VARIABLE) {
						 param[2] = new ElementVariable(token.getValue().toString());
						 token = lexer.nextToken();
					 } else {
						 param[2] = null;
					 }
					 
					 if(token.getType() == SmartScriptTokenType.TAGEND) {
						 lexer.setState(SmartScriptLexerState.TEXT);
						 mode = ParserMode.TEXT;
					 } else {
						 throw new SmartScriptParserException("Syntax error.");
					 }
					 
					 ForLoopNode forNode = new ForLoopNode(var, param[0], param[1], param[2]);
					 Node node = (Node)stack.peek();
					 node.addChildNode(forNode);
					 stack.push(forNode);
				 
				 } else if(mode == ParserMode.END) {
					 if(token.getType() == SmartScriptTokenType.TAGEND && stack.size() >= 2) {
						 lexer.setState(SmartScriptLexerState.TEXT);
						 mode = ParserMode.TEXT;
						 stack.pop();
					 } else {
						 throw new SmartScriptParserException("Syntax error.");
					 }
				 }
			 } catch(Exception e) {
				 throw new SmartScriptParserException("Cannot parse. " + e.getMessage());
			 }
		 }
		 
		 if(stack.size() == 1) {
			 documentNode = docNode;
		 } else {
			 throw new SmartScriptParserException("The document is syntactically incorrect.");
		 }
	}
}
