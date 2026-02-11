package hr.fer.zemris.java.custom.scripting.parser;

import org.junit.Test;
import org.junit.Assert;

import hr.fer.zemris.java.custom.scripting.nodes.*;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.hw03.SmartScriptTester;

public class SmartScriptParserTest {
	
	@Test(expected=NullPointerException.class)
	public void testNotNull() {
		SmartScriptParser parser = new SmartScriptParser(null);
		parser.getDocumentNode();
	}
	
	@Test
	public void testText() {
		SmartScriptParser parser = new SmartScriptParser("This is text. I love text.");
		DocumentNode docNode =  parser.getDocumentNode();
		TextNode node = (TextNode)docNode.getChild(0);
		Assert.assertEquals("This is text. I love text.", node.getText());
	}
	
	@Test
	public void testEcho1() {
		SmartScriptParser parser = new SmartScriptParser("This is text. {$= 23 4 $} I love text.");
		DocumentNode docNode =  parser.getDocumentNode();
		String s = SmartScriptTester.createOriginalDocumentBody(docNode);
		String actual = "This is text. {$=23 4$} I love text.";
		Assert.assertEquals(actual, s);
	}
	
	@Test
	public void testEcho2() {
		SmartScriptParser parser = new SmartScriptParser("This is text.{$= 2 + @func \"string\" Var 2.4  $}I love text.");
		DocumentNode docNode =  parser.getDocumentNode();
		String s = SmartScriptTester.createOriginalDocumentBody(docNode);
		String actual = "This is text.{$=2 + @func \"string\" Var 2.4$}I love text.";
		Assert.assertEquals(actual, s);
	}
	
	@Test
	public void testEcho3() {
		SmartScriptParser parser = new SmartScriptParser("text{$=echo \"string\" 2$}text\r\n{$=echo2 @func$}text");
		DocumentNode docNode =  parser.getDocumentNode();
		String s = SmartScriptTester.createOriginalDocumentBody(docNode);
		String actual = "text{$=echo \"string\" 2$}text\r\n{$=echo2 @func$}text";
		Assert.assertEquals(actual, s);
	}
	
	@Test
	public void testEcho4() {
		SmartScriptParser parser = new SmartScriptParser("{$=echo \"string\" 2$}text\r\n{$=echo2 @func$}text");
		DocumentNode docNode =  parser.getDocumentNode();
		String s = SmartScriptTester.createOriginalDocumentBody(docNode);
		String actual = "{$=echo \"string\" 2$}text\r\n{$=echo2 @func$}text";
		Assert.assertEquals(actual, s);
	}
	
	@Test
	public void testFor() {
		SmartScriptParser parser = new SmartScriptParser("{$for var  1 2 3 $}{$END$}");
		DocumentNode docNode =  parser.getDocumentNode();
		String s = SmartScriptTester.createOriginalDocumentBody(docNode);
		String actual = "{$FOR var 1 2 3$}{$END$}";
		Assert.assertEquals(actual, s);
	}
	
	@Test
	public void testAll() {
		SmartScriptParser parser = new SmartScriptParser("Tomica{$=a b \"str\" + -3.4 8  @func$}i{$fOr v 1 2$}prijatelji{$enD$}.");
		DocumentNode docNode =  parser.getDocumentNode();
		String s = SmartScriptTester.createOriginalDocumentBody(docNode);
		String actual = "Tomica{$=a b \"str\" + -3.4 8 @func$}i{$FOR v 1 2$}prijatelji{$END$}.";
		Assert.assertEquals(actual, s);
	}
}
