package hr.fer.zemris.java.hw05.db;

/**
 * This class represents a single {@link QueryLexer} token.
 * 
 * @author Mihael Stočko
 *
 */
public class QueryToken {

	/**
	 * Type of the token
	 */
	private Object type;
	
	/**
	 * Value of the token
	 */
	private Object value;
	
	/**
	 * Constructor
	 * 
	 * @param type
	 * @param value
	 */
	public QueryToken(Object type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for type
	 * 
	 * @return
	 */
	public Object getType() {
		return type;
	}

	/**
	 * Getter for value
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}
}
