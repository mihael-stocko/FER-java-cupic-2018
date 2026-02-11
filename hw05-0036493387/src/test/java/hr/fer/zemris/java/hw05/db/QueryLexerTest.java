package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class QueryLexerTest {

	@Test
	public void testLexerVariables() {
		QueryLexer lexer = new QueryLexer("jmbag");
		
		QueryToken token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("jmbag", token.getValue());
	}
	
	@Test
	public void testLexerVariablesMultiple() {
		QueryLexer lexer = new QueryLexer("jmbag firstName     lastName");
		
		QueryToken token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("jmbag", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("firstName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("lastName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.EOF, token.getType());
		Assert.assertEquals(null, token.getValue());
	}
	
	@Test
	public void testLexerOperators() {
		QueryLexer lexer = new QueryLexer("<=");
		
		QueryToken token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("<=", token.getValue());
		
		lexer = new QueryLexer("jmbag<=   firstName!=> lastName LIKE jmbag");
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("jmbag", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("<=", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("firstName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("!=", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals(">", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("lastName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("LIKE", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("jmbag", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.EOF, token.getType());
		Assert.assertEquals(null, token.getValue());
	}
	
	@Test
	public void testAndOperator() {
		QueryLexer lexer = new QueryLexer("lastName LIKE jmbag AnD firstName<");
		
		QueryToken token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("lastName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("LIKE", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("jmbag", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("AND", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("firstName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("<", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.EOF, token.getType());
		Assert.assertEquals(null, token.getValue());
	}
	
	@Test
	public void testStrings() {
		QueryLexer lexer = new QueryLexer("firstName<\"Light\" AND lastName=\"Yagami\" anD jmbag LIKE \"Kira\"");
		
		QueryToken token;
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("firstName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("<", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.STRING, token.getType());
		Assert.assertEquals("Light", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("AND", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("lastName", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("=", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.STRING, token.getType());
		Assert.assertEquals("Yagami", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("AND", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.VARIABLE, token.getType());
		Assert.assertEquals("jmbag", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.OPERATOR, token.getType());
		Assert.assertEquals("LIKE", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.STRING, token.getType());
		Assert.assertEquals("Kira", token.getValue());
		
		token = lexer.nextToken();
		Assert.assertEquals(QueryTokenType.EOF, token.getType());
		Assert.assertEquals(null, token.getValue());
	}
}
