package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A simple lexer used by the {@link QueryParser}
 * 
 * @author Mihael Stočko
 *
 */
public class QueryLexer {

	/**
	 * Constant string firstName
	 */
	private static final String first = "firstName";
	
	/**
	 * Constant string lastName
	 */
	private static final String last = "lastName";
	
	/**
	 * Constant string jmbag
	 */
	private static final String jmbag = "jmbag";
	
	/**
	 * Constant string LIKE
	 */
	private static final String like = "LIKE";
	
	/**
	 * Constant string AND
	 */
	private static final String and = "AND";
	
	/**
	 * String to be broken into tokens.
	 */
	private char[] data;
	
	/**
	 * String to be broken into tokens.
	 */
	private String dataStr;
	
	/**
	 * Index at which the lexer is currently positioned.
	 */
	private int currentIndex;
	
	/**
	 * Most recently generated token.
	 */
	private QueryToken token;
	
	/**
	 * Constructor. Accepts one string as an argument. The string cannot be null.
	 * 
	 * @param data String to be decomposed.
	 * @throws NullPointerException
	 */
	public QueryLexer(String data) {
		Objects.requireNonNull(data, "Lexer does not accept null as an argument.");
		
		this.data = data.toCharArray();
		dataStr = data;
		currentIndex = 0;
		token = null;
	}
	
	/**
	 * Returns the most recently created token.
	 * 
	 * @return token
	 */
	public QueryToken getToken() {
		return token;
	}
	
	/**
	 * Creates a new token and returns it.
	 * 
	 * @return A new token.
	 */
	public QueryToken nextToken() {
		if(currentIndex == -1) {
			throw new UnsupportedOperationException("There are no more tokens.");
		}
		
		if(currentIndex >= data.length) {
			currentIndex = -1;
			token = new QueryToken(QueryTokenType.EOF, null);
			return token;
		}
		
		removeWhitespaces();
		
		if(data.length - currentIndex >= first.length() && 
				dataStr.substring(currentIndex, currentIndex+first.length()).equals(first)) {
			currentIndex += first.length();
			token = new QueryToken(QueryTokenType.VARIABLE, first);
		} else if(data.length - currentIndex >= last.length() && 
				dataStr.substring(currentIndex, currentIndex+last.length()).equals(last)) {
			currentIndex += last.length();
			token = new QueryToken(QueryTokenType.VARIABLE, last);
		} else if(data.length - currentIndex >= jmbag.length() && 
				dataStr.substring(currentIndex, currentIndex+jmbag.length()).equals(jmbag)) {
			currentIndex += jmbag.length();
			token = new QueryToken(QueryTokenType.VARIABLE, jmbag);
		} else if(data[currentIndex] == '<') {
			if(data.length - currentIndex >= 2 && data[currentIndex+1] == '=') {
				currentIndex += 2;
				token = new QueryToken(QueryTokenType.OPERATOR, "<=");
			} else {
				currentIndex++;
				token = new QueryToken(QueryTokenType.OPERATOR, "<");
			}
		} else if(data[currentIndex] == '>') {
			if(data.length - currentIndex >= 2 && data[currentIndex+1] == '=') {
				currentIndex += 2;
				token = new QueryToken(QueryTokenType.OPERATOR, ">=");
			} else {
				currentIndex++;
				token = new QueryToken(QueryTokenType.OPERATOR, ">");
			}
		} else if(data[currentIndex] == '=') {
			currentIndex++;
			token = new QueryToken(QueryTokenType.OPERATOR, "=");
		} else if(data[currentIndex] == '!' && data.length - currentIndex >= 2 && 
				data[currentIndex + 1] == '=') {
			currentIndex += 2;
			token = new QueryToken(QueryTokenType.OPERATOR, "!=");
		} else if(data.length - currentIndex >= like.length() && 
				dataStr.substring(currentIndex, currentIndex+like.length()).equals(like)) {
			currentIndex += like.length();
			token = new QueryToken(QueryTokenType.OPERATOR, like);
		} else if(data.length - currentIndex >= and.length() && 
				dataStr.substring(currentIndex, currentIndex+and.length()).toUpperCase().equals(and)) {
			currentIndex += and.length();
			token = new QueryToken(QueryTokenType.OPERATOR, and);
		} else if(data[currentIndex] == '"') {
			token = parseString();
		} else {
			throw new IllegalArgumentException("Cannot produce token.");
		}
		
		return token;
	}

	/**
	 * This is used for parsing Strings
	 * 
	 * @return A new token.
	 */
	private QueryToken parseString() {
		currentIndex++;
		StringBuilder sb = new StringBuilder();
		while(currentIndex < data.length) {
			if(data[currentIndex] == '"') {
				currentIndex++;
				break;
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		return new QueryToken(QueryTokenType.STRING, sb.toString());
	}
	
	/**
	 * Removes all whitespaces between the current index (included) and 
	 * the first character that is not a whitespace.
	 */
	private void removeWhitespaces() {
		while(currentIndex < data.length) {
			if(data[currentIndex] == ' ' || data[currentIndex] == '\r' ||
					data[currentIndex] == '\n'|| data[currentIndex] == '\t') {
				currentIndex++;
			} else {
				break;
			}
		}
	}
}
