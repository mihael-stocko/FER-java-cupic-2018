package hr.fer.zemris.java.hw03.prob1;

/**
 * A representation of a token being returned by the lexer.
 * 
 * @author Mihael Stočko
 *
 */
public class Token {

	/**
	 * Type of the token.
	 */
	private TokenType type;
	
	/**
	 * Value of the token.
	 */
	private Object value;
	
	/**
	 * Constructor. Takes the type and the value of the token being created.
	 * 
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return Value of the token.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Getter for type.
	 * 
	 * @return Type of the token.
	 */
	public TokenType getType() {
		return type;
	}
}
