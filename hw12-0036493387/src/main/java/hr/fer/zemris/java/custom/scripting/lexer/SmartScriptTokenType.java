package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Possible SmartScriptToken types.
 * 
 * @author Mihael Stočko
 *
 */
public enum SmartScriptTokenType {
	TAGSTART, TAGEND, INTEGER, DOUBLE, STRING, OPERATOR, FUNCTION, VARIABLE, EOF;
}
