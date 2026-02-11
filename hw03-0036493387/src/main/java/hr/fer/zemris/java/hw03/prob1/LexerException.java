package hr.fer.zemris.java.hw03.prob1;

/**
 * The exception that can be thrown by the lexer.
 * 
 * @author Mihael Stočko
 *
 */
public class LexerException extends RuntimeException {

	public static final long serialVersionUID = 1L;
	
	public LexerException() {
		super();
	}
	
	public LexerException(String message) {
		super(message);
	}
}
