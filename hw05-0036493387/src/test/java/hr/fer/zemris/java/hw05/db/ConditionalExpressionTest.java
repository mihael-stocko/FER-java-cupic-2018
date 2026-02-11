package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

public class ConditionalExpressionTest {
	
	StudentRecord record;
	
	@Before
	public void initialize() {
		record = new StudentRecord("0000000000", "Baggins", "Sam", 1);
	}
	
	@Test
	public void testGetFieldGetterLike() {
		ConditionalExpression expr = new ConditionalExpression( FieldValueGetters.LAST_NAME, 
				"Bag*", ComparisonOperators.LIKE);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record),
				 expr.getStringLiteral()
			);
		
		Assert.assertTrue(recordSatisfies);
	}
	
	@Test
	public void testGetFieldGetterLess() {
		ConditionalExpression expr = new ConditionalExpression( FieldValueGetters.FIRST_NAME, 
				"Saruman", ComparisonOperators.LESS);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record),
				 expr.getStringLiteral()
			);
		
		Assert.assertTrue(recordSatisfies);
	}
	
	@Test
	public void testGetFieldGetterEquals() {
		ConditionalExpression expr = new ConditionalExpression( FieldValueGetters.JMBAG, 
				"0000000000", ComparisonOperators.EQUALS);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record),
				 expr.getStringLiteral()
			);
		
		Assert.assertTrue(recordSatisfies);
	}
	
	@Test
	public void testGetFieldGetterGreater() {
		ConditionalExpression expr = new ConditionalExpression( FieldValueGetters.LAST_NAME, 
				"The One Ring", ComparisonOperators.GREATER);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record),
				 expr.getStringLiteral()
			);
		
		Assert.assertFalse(recordSatisfies);
	}
}
