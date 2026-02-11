package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A representation of a token being returned by the SmartScriptLexer.
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptToken {
	
	/**
	 * Type of the token.
	 */
	private SmartScriptTokenType type;
	
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
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for type.
	 * 
	 * @return Type of the token.
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return Value of the token.
	 */
	public Object getValue() {
		return value;
	}
}
