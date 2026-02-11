package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A lexer used by SmartScriptParser. Takes a string and decomposes it into a stream of tokens.
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptLexer {
	
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
	private SmartScriptToken token;
	
	/**
	 * The state of the lexer. Lexer's behaviour is determined by this.
	 */
	private SmartScriptLexerState state;
	
	/**
	 * Constructor. Accepts one string as an argument. The string cannot be null.
	 * 
	 * @param text String to be decomposed.
	 * @throws IllegalArgumentException
	 */
	public SmartScriptLexer(String text) {
		if(text == null) {
			throw new IllegalArgumentException("null is not an acceptable argument for the lexer.");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = SmartScriptLexerState.TEXT;
	}
	
	/**
	 * Sets the state of the lexer.
	 * 
	 * @param state A new state.
	 */
	public void setState(SmartScriptLexerState state) {
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
	public SmartScriptToken getToken() {
		if(token == null) {
			throw new SmartScriptLexerException("There are no tokens.");
		}
		return token;
	}
	
	/**
	 * Creates a new token and returns it.
	 * 
	 * @return A new token.
	 * @throws SmartScriptLexerException
	 */
	public SmartScriptToken nextToken() {
		if(currentIndex == -1) {
			throw new SmartScriptLexerException("There are no more tokens.");
		}
		
		if(currentIndex >= data.length) {
			currentIndex = -1;
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}
		
		if(state == SmartScriptLexerState.TEXT) {
			return parseText();
		} else {
			return parseTag();
		}
	}
	
	/**
	 * This is called when the SmartScriptLexer is in TEXT state.
	 * Creates one token from the string and returns it.
	 * 
	 * @return A new token.
	 */
	private SmartScriptToken parseText() {
		if(data[currentIndex] == '{') {
			if(currentIndex == data.length-1) {
				throw new SmartScriptLexerException("Invalid input format.");
			} else {
				if(data[currentIndex+1] == '$') {
					token = new SmartScriptToken(SmartScriptTokenType.TAGSTART, null);
					currentIndex += 2;
					return token;
				} else {
					throw new SmartScriptLexerException("Invalid input format.");
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length) {
			if(data[currentIndex] == '{') {
				break;
			} else if(data[currentIndex] == '\\') {
				if(currentIndex == data.length-1) {
					throw new SmartScriptLexerException("Invalid input format.");
				} else {
					if(data[currentIndex+1] == '\\' || data[currentIndex+1] == '{') {
						sb.append(data[currentIndex+1]);
						currentIndex += 2;
					} else {
						throw new SmartScriptLexerException("Invalid input format.");
					}
				}
			} else {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}
		
		String s = sb.toString();
		token = new SmartScriptToken(SmartScriptTokenType.STRING, s);
		return token;
	}
	
	/**
	 * This is called when the SmartScriptLexer is in TAG state.
	 * Creates one token from the string and returns it.
	 * 
	 * @return A new token.
	 */
	private SmartScriptToken parseTag() {
		removeWhitespaces();
		if(data[currentIndex] == '$') {
			if(currentIndex == data.length-1) {
				throw new SmartScriptLexerException("Invalid input format.");
			}
			if(data[currentIndex+1] == '}') {
				currentIndex += 2;
				token = new SmartScriptToken(SmartScriptTokenType.TAGEND, null);
				return token;
			} else {
				throw new SmartScriptLexerException("Invalid input format.");
			}
		}
		
		StringBuilder sb = new StringBuilder();
		boolean negative = false;
		
		if(Character.isLetter(data[currentIndex])) {
			parseVariable(sb);
			
			String s = sb.toString();
			token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, s);
			return token;
		} else if(data[currentIndex] == '@') {
			if(currentIndex == data.length-1) {
				throw new SmartScriptLexerException("Invalid function name syntax.");
			}
			currentIndex++;
			if(!Character.isLetter(data[currentIndex])) {
				throw new SmartScriptLexerException("Invalid function name syntax.");
			} else {
				parseVariable(sb);
				
				String s = sb.toString();
				token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, s);
				return token;
			}
		} else if(data[currentIndex] == '"') {
			boolean closed = false;
			currentIndex++;
			
			while(currentIndex < data.length) {
				if(data[currentIndex] == '\\') {
					if(currentIndex == data.length-1) {
						throw new SmartScriptLexerException("Invalid string format.");
					} else {
						if(data[currentIndex+1] == '\\' || data[currentIndex+1] == '"') {
							sb.append(data[currentIndex+1]);
							currentIndex += 2;
						} else if(data[currentIndex+1] == 'r') {
							sb.append('\r');
							currentIndex += 2;
						} else if (data[currentIndex+1] == 'n') {
							sb.append('\n');
							currentIndex += 2;
						} else if(data[currentIndex+1] == 't') {
							sb.append('\t');
							currentIndex += 2;
						} else {
							throw new SmartScriptLexerException("Invalid string format.");
						}
					}
				} else if(data[currentIndex] == '"') {
					closed = true;
					currentIndex++;
					break;
				} else {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
			}
			
			if(!closed) {
				throw new SmartScriptLexerException("Invalid string format.");
			} else {
				String s = sb.toString();
				token =  new SmartScriptToken(SmartScriptTokenType.STRING, s);
				return token;
			}
		} else if(data[currentIndex] == '+' || data[currentIndex] == '*' || 
				data[currentIndex] == '/' || data[currentIndex] == '^' || data[currentIndex] == '=') {
			token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex]);
			currentIndex++;
			return token;
		} else if(data[currentIndex] == '-') {
			if(currentIndex == data.length-1 || !isInteger(data[currentIndex+1])) {
				token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex]);
				currentIndex++;
				return token;
			} else {
				negative = true;
				currentIndex++;
			}
		}
		if(isInteger(data[currentIndex])) {
			parseNumber(sb);
			String s = sb.toString();
			
			try {
				int x = Integer.parseInt(s);
				if(negative) {
					x = -x;
				}
				token = new SmartScriptToken(SmartScriptTokenType.INTEGER, x);
			} catch(Exception e) {
				double x = Double.parseDouble(s);
				if(negative) {
					x = -x;
				}
				token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, x);
			}
			
			return token;
		}
		
		throw new SmartScriptLexerException("Invalid tag format.");
	}
	
	/**
	 * Parses a variable and appends it to the StringBuilder.
	 * 
	 * @param sb StringBuilder
	 */
	private void parseVariable(StringBuilder sb) {
		while(currentIndex < data.length) {
			if(Character.isLetter(data[currentIndex]) || isInteger(data[currentIndex]) 
					|| data[currentIndex] == '_') {
				sb.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
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
	 * Parses a number and appends it to the StringBuilder.
	 * 
	 * @param sb StringBuilder
	 */
	private void parseNumber(StringBuilder sb) {
		while(currentIndex < data.length) {
			if(isInteger(data[currentIndex]) || data[currentIndex] == '.') {
				sb.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
	}
}
