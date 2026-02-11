package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The exception that can be thrown by SmartScriptLexer.
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptLexerException extends RuntimeException {
	
	public static final long serialVersionUID = 1L;
	
	public SmartScriptLexerException() {
		super();
	}
	
	public SmartScriptLexerException(String message) {
		super(message);
	}
}

