package hr.fer.zemris.math;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {

	public static final double epsilon = 1e-6;
	
	@Test
	public void testDefault() {
		Complex c1 = Complex.ZERO;
		Complex c2 = Complex.ONE;
		Complex c3 = Complex.ONE_NEG;
		Complex c4 = Complex.IM;
		Complex c5 = Complex.IM_NEG;
		
		Assert.assertEquals(0, c1.getRe(), epsilon);
		Assert.assertEquals(0, c1.getIm(), epsilon);
		Assert.assertEquals(1, c2.getRe(), epsilon);
		Assert.assertEquals(0, c2.getIm(), epsilon);
		Assert.assertEquals(-1, c3.getRe(), epsilon);
		Assert.assertEquals(0, c3.getIm(), epsilon);
		Assert.assertEquals(0, c4.getRe(), epsilon);
		Assert.assertEquals(1, c4.getIm(), epsilon);
		Assert.assertEquals(0, c5.getRe(), epsilon);
		Assert.assertEquals(-1, c5.getIm(), epsilon);
	}
	
	@Test
	public void testConstructor() {
		Complex c = new Complex(1, 2);
		Assert.assertEquals(1, c.getRe(), epsilon);
		Assert.assertEquals(2, c.getIm(), epsilon);
	}
	
	@Test
	public void testModule() {
		Complex c = new Complex(3, 4);
		Assert.assertEquals(5, c.module(), epsilon);
		c = new Complex(-3, 8);
		Assert.assertEquals(8.544004, c.module(), epsilon);
	}
	
	@Test
	public void testMultiply() {
		Complex c1 = new Complex(1, 1);
		Complex c2 = new Complex(3, -4.5);
		Complex c3 = new Complex(3.8, -4.2);
		Complex result = c1.multiply(c2).multiply(c3);
		Assert.assertEquals(22.2, result.getRe(), 1E-6);
		Assert.assertEquals(-37.2, result.getIm(), 1E-6);
		result = result.multiply(new Complex(10, 0));
		Assert.assertEquals(222, result.getRe(), 1E-6);
		Assert.assertEquals(-372, result.getIm(), 1E-6);
	}
	
	@Test
	public void testDivide() {
		Complex c1 = new Complex(1, 1);
		Complex c2 = new Complex(3, -4.5);
		Complex c3 = new Complex(3.8, -4.2);
		Complex result = c1.divide(c2);
		Assert.assertEquals(-0.051282, result.getRe(), 1E-6);
		Assert.assertEquals(0.256410, result.getIm(), 1E-6);
		result = result.divide(c3);
		Assert.assertEquals(-0.039645, result.getRe(), 1E-6);
		Assert.assertEquals(0.023659, result.getIm(), 1E-6);
		result = result.divide(new Complex(10, 0));
		Assert.assertEquals(-0.003964, result.getRe(), 1E-6);
		Assert.assertEquals(0.002366, result.getIm(), 1E-6);
	}
	
	@Test
	public void testAdd() {
		Complex c1 = new Complex(1, 1);
		Complex c2 = new Complex(3, -4.5);
		Complex c3 = new Complex(0, 0);
		Complex result = c1.add(c2.add(c3.add(new Complex(10, 0))));
		Assert.assertEquals(14, result.getRe(), 1E-6);
		Assert.assertEquals(-3.5, result.getIm(), 1E-6);
	}
	
	@Test
	public void testSubtract() {
		Complex c1 = new Complex(1, 1);
		Complex c2 = new Complex(3, -4.5);
		Complex c3 = new Complex(0, 0);
		Complex result = c1.sub(c2.sub(c3.sub(new Complex(10, 0))));
		Assert.assertEquals(-12, result.getRe(), 1E-6);
		Assert.assertEquals(5.5, result.getIm(), 1E-6);
	}
	
	@Test
	public void testNegate() {
		Complex c = new Complex(1, 2);
		Assert.assertEquals(-1, c.negate().getRe(), 1E-6);
		Assert.assertEquals(-2, c.negate().getIm(), 1E-6);
	}
	
	@Test
	public void powerTest() {
		Complex c = new Complex(2, 3);
		Complex result = c.power(2);
		Assert.assertEquals(-5, result.getRe(), 1E-6);
		Assert.assertEquals(12, result.getIm(), 1E-6);
		c = new Complex(3, -5);
		result = c.power(4);
		Assert.assertEquals(-644, result.getRe(), 1E-6);
		Assert.assertEquals(960, result.getIm(), 1E-6);
	}
	
	@Test
	public void rootTest() {
		Complex c = new Complex(-3, 4.5);
		List<Complex> complexList = c.root(3);
		Assert.assertEquals(1.3201, complexList.get(0).getRe(), 1E-4);
		Assert.assertEquals(1.1569, complexList.get(0).getIm(), 1E-4);
		Assert.assertEquals(-1.66196, complexList.get(1).getRe(), 1E-4);
		Assert.assertEquals(0.5648, complexList.get(1).getIm(), 1E-4);
		Assert.assertEquals(0.3418, complexList.get(2).getRe(), 1E-4);
		Assert.assertEquals(-1.7217, complexList.get(2).getIm(), 1E-4);
	}
}
