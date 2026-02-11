package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class ComparisonOperatorsTest {

	@Test
	public void lessTest() {
		Assert.assertTrue(ComparisonOperators.LESS.satisfied("crv", "glista"));
		Assert.assertFalse(ComparisonOperators.LESS.satisfied("ogroma ribica", "mala ribica"));
		Assert.assertTrue(ComparisonOperators.LESS.satisfied("brod", "tenk"));
	}
	
	@Test
	public void lessOrEqualsTest() {
		Assert.assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("", ""));
		Assert.assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("a", "b"));
		Assert.assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("ab", "aa"));
		Assert.assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("brod", "tenk"));
		Assert.assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("a", "a"));
		Assert.assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("xx", "xx"));
		Assert.assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("štuketina", "tunjevina"));
	}
	
	@Test
	public void greaterTest() {
		Assert.assertTrue(ComparisonOperators.GREATER.satisfied("slon", "majmun"));
		Assert.assertFalse(ComparisonOperators.GREATER.satisfied("covjek", "godzilla"));
		Assert.assertTrue(ComparisonOperators.GREATER.satisfied("INTJ", "ENTJ"));
	}
	
	@Test
	public void greaterOrEqualsTest() {
		Assert.assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("", ""));
		Assert.assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("kuća", "glupost"));
		Assert.assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("riječ majmun", "riječ majica"));
		Assert.assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("slovo a", "slovo c"));
		Assert.assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("nešto kreativno", "nešto crknuto"));
		Assert.assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("spavanje", "zzzz"));
	}
	
	@Test
	public void equalsTest() {
		Assert.assertTrue(ComparisonOperators.EQUALS.satisfied("", ""));
		Assert.assertTrue(ComparisonOperators.EQUALS.satisfied("smeće", "smeće"));
		Assert.assertFalse(ComparisonOperators.EQUALS.satisfied("staklo", "plastika"));
		Assert.assertFalse(ComparisonOperators.EQUALS.satisfied("papir", "kompost"));
	}
	
	@Test
	public void notEqualsTest() {
		Assert.assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("", "hehehe"));
		Assert.assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("bakterije", "virusi"));
		Assert.assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("gusjenica", "gusjenica"));
		Assert.assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("vodene životinje", "vodene životinje"));
	}
	
	@Test
	public void likeTest() {
		Assert.assertTrue(ComparisonOperators.LIKE.satisfied("Nuštar", "Nuštar"));
		Assert.assertTrue(ComparisonOperators.LIKE.satisfied("Zagreb", "Zag*"));
		Assert.assertTrue(ComparisonOperators.LIKE.satisfied("Zagreb", "*greb"));
		Assert.assertTrue(ComparisonOperators.LIKE.satisfied("Zagreb", "Za*eb"));
		Assert.assertFalse(ComparisonOperators.LIKE.satisfied("Dopsin", "Vin*"));
		Assert.assertFalse(ComparisonOperators.LIKE.satisfied("Dopsin", "*ulja"));
		Assert.assertFalse(ComparisonOperators.LIKE.satisfied("Dopsin", "Vin*ulja"));
		Assert.assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
		Assert.assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		Assert.assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void likeTestMoreThanOneWildcard() {
		ComparisonOperators.LIKE.satisfied("Prkovci", "Pr*o*i");
	}
	
	@Test(expected=NullPointerException.class)
	public void testLessNull() {
		ComparisonOperators.LESS.satisfied(null, "groblje");
	}
	
	@Test(expected=NullPointerException.class)
	public void testEqualsNull() {
		ComparisonOperators.EQUALS.satisfied("smrt", null);
	}
}
