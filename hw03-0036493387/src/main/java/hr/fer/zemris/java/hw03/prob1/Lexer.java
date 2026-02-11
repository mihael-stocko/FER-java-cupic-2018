package hr.fer.zemris.java.hw03.prob1;

/**
 * A simple lexer. Takes a string and decomposes it into a stream of tokens.
 * 
 * @author Mihael Stočko
 *
 */
public class Lexer {
	
	/**
	 * String to be broken into tokens.
	 */
	private char[] data;
	
	/**
	 * Index at which the lexer is currently positioned.
	 */
	private int currentIndex;
	
	/**
	 * Most recently generated token.
	 */
	private Token token;
	
	/**
	 * The state of the lexer. Lexer's behaviour is determined by this.
	 */
	private LexerState state;
	
	/**
	 * Constructor. Accepts one string as an argument. The string cannot be null.
	 * 
	 * @param text String to be decomposed.
	 * @throws IllegalArgumentException
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new IllegalArgumentException("null is not an acceptable argument for the lexer.");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Sets the state of the lexer.
	 * 
	 * @param state A new state.
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new IllegalArgumentException("Argument for setState cannot be null.");
		}
		this.state = state;
	}
	
	/**
	 * Returns the most recently created token.
	 * 
	 * @return token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Creates a new token and returns it.
	 * 
	 * @return A new token.
	 * @throws LexerException
	 */
	public Token nextToken() {
		if(currentIndex == -1) {
			throw new LexerException("There are no more tokens.");
		}
		
		removeWhitespaces();
		
		if(currentIndex >= data.length) {
			currentIndex = -1;
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if(data[currentIndex] == '#') {
			currentIndex++;
			token = new Token(TokenType.SYMBOL, '#');
			return token;
		}
		
		StringBuilder sb = new StringBuilder();
		
		if(state.equals(LexerState.BASIC)) {
			parseWord(sb);
			String s = sb.toString();
			if(s.length() > 0) {
				token = new Token(TokenType.WORD, s);
				return token;
			}
			
			parseNumber(sb);
			s = sb.toString();
			if(s.length() > 0) {
				try {
					token = new Token(TokenType.NUMBER, Long.parseLong(s));
					return token;
				} catch(Exception e) {
					throw new LexerException("The number is too big to be represented as a long.");
				}
			}
			
			if(data[currentIndex] == '\\') {
				throw new LexerException("Wrong input format.");
			} else {
				token = new Token(TokenType.SYMBOL, data[currentIndex]);
				currentIndex++;
				return token;
			}
		} else {
			parseEverything(sb);
			String s = sb.toString();
			token = new Token(TokenType.WORD, s);
			return token;
		}
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
	
	/**
	 * Parses a string into tokens. Single tokens are separated by whitespaces.
	 * 
	 * @param sb StringBuilder
	 */
	private void parseWord(StringBuilder sb) {
		while(currentIndex < data.length) {
			if(Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			} else if(data[currentIndex] == '\\') {
				if(currentIndex == data.length-1) {
					throw new LexerException("Wrong input format.");
				} else {
					if(isInteger(data[currentIndex+1]) || data[currentIndex+1] == '\\') {
						sb.append(data[currentIndex+1]);
						currentIndex += 2;
					} else {
						throw new LexerException("Wrong input format.");
					}
				}
			} else {
				break;
			}
		}
	}
	
	/**
	 * Parses a number and appends it to the StringBuilder.
	 * 
	 * @param sb StringBuilder
	 */
	private void parseNumber(StringBuilder sb) {
		while(currentIndex < data.length) {
			if(isInteger(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Determined if the given character is a number.
	 *
	 * @param c Character to be tested.
	 * @return <code>true</code> if c is a number, <code>false</code> otherwise.
	 */
	private boolean isInteger(char c) {
		try {
			String s = "" + c;
			Integer.parseInt(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Used for parsing when the lexer is in the EXTENDED state.
	 * 
	 * @param sb
	 */
	private void parseEverything(StringBuilder sb) {
		while(currentIndex < data.length) {
			if(data[currentIndex] != ' ' && data[currentIndex] != '\t' && 
					data[currentIndex] != '\r' && data[currentIndex] != '\n' && 
					data[currentIndex] != '#') {
				sb.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
	}
}
