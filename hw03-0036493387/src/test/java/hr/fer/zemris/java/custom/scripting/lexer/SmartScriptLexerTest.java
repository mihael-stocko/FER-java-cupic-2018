package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new SmartScriptLexer(null);
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals("Empty input must generate only EOF token.", SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testText1() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija automobil.");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija automobil."),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
	
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testText2() {
		SmartScriptLexer lexer = new SmartScriptLexer("    Štefanija automobil.   ");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "    Štefanija automobil.   "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
	
		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWrongInput1() {
		SmartScriptLexer lexer = new SmartScriptLexer("    Štefanija automobil.   {");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWrongInput2() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\z  Štefanija automobil.   ");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWrongInput3() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \t  Štefanija automobil.   \\");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWrongInput4() {
		SmartScriptLexer lexer = new SmartScriptLexer(" {   Štefanija automobil.   {");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testEscapeSequences() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija. \\\\ Štefanija. \\{ Štefanija.");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija. \\ Štefanija. { Štefanija."),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagTokens() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija.{$");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija."),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullState() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(null);
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testTagWrongFormat1() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$$");
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testTagWrongFormat2() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$     $z");
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
	}
	
	@Test
	public void testTagClosing() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$     $}Štefanija{$    $}Štefanija");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testVariables() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$ var1  var_2  $}Štefanija");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var1"),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var_2"),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testFunctions() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$ var1 @func var_2 @f_3u  $}Štefanija");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var1"),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "func"),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var_2"),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "f_3u"),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testStrings() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$ var1 @func \"string\\\\\"   \" str2 \\\"\"  $}Štefanija");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var1"),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "func"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "string\\"),
				new SmartScriptToken(SmartScriptTokenType.STRING, " str2 \""),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testStringNotClosed() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$ var1 @func \"string\\\\  $}Štefanija");
		
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
		checkState(lexer);
		lexer.nextToken();
	}
	
	@Test
	public void testOperators() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$ var1+@func-\"string\\\\\"$}Štefanija");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var1"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '+'),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "func"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '-'),
				new SmartScriptToken(SmartScriptTokenType.STRING, "string\\"),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testNumbers() {
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija{$ var1+@func-\"string\\\\\"  2 -3 4.5$}Štefanija");
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var1"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '+'),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "func"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '-'),
				new SmartScriptToken(SmartScriptTokenType.STRING, "string\\"),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, 2),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, -3),
				new SmartScriptToken(SmartScriptTokenType.DOUBLE, 4.5),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void theOneUltimateTest() {
		String s = new String("");
		s += "Štefanija{$ var1+@func-\"string\\\\\"  2 -3 4.5$}";
		s += "Autumobil{$  -4.76  @funkc_1ja \"štefica\"  / = * ^   $}";
		
		SmartScriptLexer lexer = new SmartScriptLexer(s);
		
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, "Štefanija"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "var1"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '+'),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "func"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '-'),
				new SmartScriptToken(SmartScriptTokenType.STRING, "string\\"),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, 2),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, -3),
				new SmartScriptToken(SmartScriptTokenType.DOUBLE, 4.5),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "Autumobil"),
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, null),
				new SmartScriptToken(SmartScriptTokenType.DOUBLE, -4.76),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "funkc_1ja"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "štefica"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '/'),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '='),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '*'),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, '^'),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	private void checkState(SmartScriptLexer lexer) {
		if(lexer.getToken().getType() == SmartScriptTokenType.TAGSTART) {
			lexer.setState(SmartScriptLexerState.TAG);
		}
		if(lexer.getToken().getType() == SmartScriptTokenType.TAGEND) {
			lexer.setState(SmartScriptLexerState.TEXT);
		}
	}
	
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for(SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			checkState(lexer);
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
}
