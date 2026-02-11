package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import java.util.List;
import java.util.LinkedList;
import org.junit.Assert;

public class QueryParserTest {
	
	@Test
	public void testIsDirectQuery() {
		QueryParser parser = new QueryParser("jmbag=\"12345\"");
		Assert.assertTrue(parser.isDirectQuery());
		
		parser = new QueryParser("jmbag=\"12345\" AND firstName=\"bunbar\"");
		Assert.assertFalse(parser.isDirectQuery());
		
	}
	
	@Test
	public void testGetQueriedJMBAG() {
		QueryParser parser = new QueryParser("jmbag=\"12345\"");
		Assert.assertEquals("12345", parser.getQueriedJMBAG());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testGetQueriedJMBAG2() {
		QueryParser parser = new QueryParser("jmbag=\"12345\" AND firstName=\"bunbar\"");
		parser.getQueriedJMBAG();
	}
	
	@Test
	public void testGetQuery() {
		QueryParser parser = new QueryParser("firstName<\"Ninten\" aNd jmbag!=\"00000\" and"
				+ " lastName=\"Ness\" AND firstName LIKE \"Lucas\"");
		
		List<ConditionalExpression> liszt = parser.getQuery();
		
		Assert.assertEquals(4, liszt.size());
		
		int i = 0;
		Assert.assertEquals(FieldValueGetters.FIRST_NAME, liszt.get(i).getFieldGetter());
		Assert.assertEquals(ComparisonOperators.LESS, liszt.get(i).getComparisonOperator());
		Assert.assertEquals("Ninten", liszt.get(i).getStringLiteral());
		
		i++;
		Assert.assertEquals(FieldValueGetters.JMBAG, liszt.get(i).getFieldGetter());
		Assert.assertEquals(ComparisonOperators.NOT_EQUALS, liszt.get(i).getComparisonOperator());
		Assert.assertEquals("00000", liszt.get(i).getStringLiteral());
		
		i++;
		Assert.assertEquals(FieldValueGetters.LAST_NAME, liszt.get(i).getFieldGetter());
		Assert.assertEquals(ComparisonOperators.EQUALS, liszt.get(i).getComparisonOperator());
		Assert.assertEquals("Ness", liszt.get(i).getStringLiteral());
		
		i++;
		Assert.assertEquals(FieldValueGetters.FIRST_NAME, liszt.get(i).getFieldGetter());
		Assert.assertEquals(ComparisonOperators.LIKE, liszt.get(i).getComparisonOperator());
		Assert.assertEquals("Lucas", liszt.get(i).getStringLiteral());
	}
}
