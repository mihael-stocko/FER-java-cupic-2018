package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;
import org.junit.Assert;

public class ValueWrapperTest {

	public static double epsilon = 1e-6;
	
	@Test(expected=RuntimeException.class)
	public void testAddWrongStringFormat() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.add("Štefica");
	}
	
	@Test(expected=RuntimeException.class)
	public void testSubWrongStringFormat() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.subtract("Štefica");
	}
	
	@Test(expected=RuntimeException.class)
	public void testMulWrongStringFormat() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.multiply("Štefica");
	}
	
	@Test(expected=RuntimeException.class)
	public void testDivWrongStringFormat() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.divide("Štefica");
	}
	
	@Test
	public void testAdd() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.add(null);
		Assert.assertEquals(0, wrapper.getValue());
		
		wrapper = new ValueWrapper(5);
		wrapper.add(10);
		Assert.assertEquals(15, wrapper.getValue());
		
		wrapper = new ValueWrapper(5);
		wrapper.add(10.2);
		Assert.assertEquals(15.2, wrapper.getValue());
		
		wrapper = new ValueWrapper(5.8);
		wrapper.add(10);
		Assert.assertEquals(15.8, wrapper.getValue());
		
		wrapper = new ValueWrapper(5.3);
		wrapper.add(11.8);
		Assert.assertEquals(17.1, wrapper.getValue());
		
		wrapper = new ValueWrapper(5);
		wrapper.add("1.02e1");
		Assert.assertEquals(15.2, wrapper.getValue());
	}
	
	@Test
	public void testSubtract() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.subtract(null);
		Assert.assertEquals(0, wrapper.getValue());
		
		wrapper = new ValueWrapper(5);
		wrapper.subtract(10);
		Assert.assertEquals(-5, wrapper.getValue());
		
		wrapper = new ValueWrapper(5);
		wrapper.subtract(10.2);
		Assert.assertEquals(-5.2, ((Double)wrapper.getValue()).doubleValue(), epsilon);
		
		wrapper = new ValueWrapper(15.8);
		wrapper.subtract(10);
		Assert.assertEquals(5.8, ((Double)wrapper.getValue()).doubleValue(), epsilon);
		
		wrapper = new ValueWrapper(11.8);
		wrapper.subtract(5.3);
		Assert.assertEquals(6.5, ((Double)wrapper.getValue()).doubleValue(), epsilon);
		
		wrapper = new ValueWrapper(11.8);
		wrapper.subtract("5.3");
		Assert.assertEquals(6.5, ((Double)wrapper.getValue()).doubleValue(), epsilon);
	}
	
	@Test
	public void testMultiply() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.multiply(null);
		Assert.assertEquals(0, wrapper.getValue());
		
		wrapper = new ValueWrapper(5);
		wrapper.multiply(10);
		Assert.assertEquals(50, wrapper.getValue());
		
		wrapper = new ValueWrapper(11.8);
		wrapper.multiply("5.3");
		Assert.assertEquals(62.54, ((Double)wrapper.getValue()).doubleValue(), epsilon);
	}
	
	@Test
	public void testDivide() {
		ValueWrapper wrapper = new ValueWrapper(10);
		wrapper.divide(2);
		Assert.assertEquals(5, wrapper.getValue());
		
		wrapper = new ValueWrapper(10.5);
		wrapper.divide(0.5);
		Assert.assertEquals(21, ((Double)wrapper.getValue()).doubleValue(), epsilon);
		
		wrapper = new ValueWrapper(13.8);
		wrapper.divide(1.2);
		Assert.assertEquals(11.5, ((Double)wrapper.getValue()).doubleValue(), epsilon);
	}
	
	@Test(expected=RuntimeException.class)
	public void testDivideWithZero() {
		ValueWrapper wrapper = new ValueWrapper(10);
		wrapper.divide(null);
	}
	
	@Test(expected=RuntimeException.class)
	public void testNumCompareWrongStringFormat() {
		ValueWrapper wrapper = new ValueWrapper(10);
		wrapper.numCompare("strng");
	}
	
	@Test
	public void testNumCompare() {
		ValueWrapper wrapper = new ValueWrapper(10);
		Assert.assertEquals(0, wrapper.numCompare(10));
		
		wrapper = new ValueWrapper(10);
		Assert.assertEquals(1, wrapper.numCompare(5));
		
		wrapper = new ValueWrapper(10);
		Assert.assertEquals(-1, wrapper.numCompare(15));
		
		wrapper = new ValueWrapper(10.5);
		Assert.assertEquals(0, wrapper.numCompare(10.5));
		
		wrapper = new ValueWrapper(10.5);
		Assert.assertEquals(-1, wrapper.numCompare(10.6));
		
		wrapper = new ValueWrapper(10.5);
		Assert.assertEquals(1, wrapper.numCompare(10.4));
	}
	
	@Test
	public void testAll() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		// v1 now stores Integer(0); v2 still stores null.
		Assert.assertEquals(0, v1.getValue());
		Assert.assertNull(v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		// v3 now stores Double(13); v4 still stores Integer(1).
		Assert.assertEquals(13, ((Double)v3.getValue()).doubleValue(), epsilon);
		Assert.assertEquals(1, v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		// v5 now stores Integer(13); v6 still stores Integer(1).
		Assert.assertEquals(13, v5.getValue());
		Assert.assertEquals(1, v6.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testAnkica() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());
	}
}
