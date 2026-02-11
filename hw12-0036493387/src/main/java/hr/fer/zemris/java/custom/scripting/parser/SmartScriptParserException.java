package hr.fer.zemris.java.custom.scripting.parser;

/**
 * The exception that can be thrown by SmartScriptParser
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptParserException extends RuntimeException {
	public static final long serialVersionUID = 1L;
	
	public SmartScriptParserException() {
		super();
	}
	
	public SmartScriptParserException(String message) {
		super(message);
	}
}
