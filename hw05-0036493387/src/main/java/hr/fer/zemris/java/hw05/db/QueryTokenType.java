package hr.fer.zemris.java.hw05.db;

/**
 * This enumeration defines the possible token types for the {@link QueryLexer}.
 * 
 * @author Miguel Stočko
 *
 */
public enum QueryTokenType {
	
	/**
	 * Name of the {@link StudentRecord} field
	 */
	VARIABLE, 
	
	/**
	 * Text under quotations
	 */
	STRING, 
	
	/**
	 * Operators <, >, <=, >=, =, !=, AND and LIKE
	 */
	OPERATOR, 
	
	/**
	 * End of file
	 */
	EOF;
}
