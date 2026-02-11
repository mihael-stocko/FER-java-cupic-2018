package hr.fer.zemris.java.hw02;

import org.junit.Test;
import org.junit.Assert;

public class ComplexNumberTest {	
	
	@Test
	public void fromRealTest() {
		ComplexNumber complex = ComplexNumber.fromReal(5);
		Assert.assertEquals(5, complex.getReal(), 1E-6);
		Assert.assertEquals(0, complex.getImaginary(), 1E-6);
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber complex = ComplexNumber.fromImaginary(5);
		Assert.assertEquals(0, complex.getReal(), 1E-6);
		Assert.assertEquals(5, complex.getImaginary(), 1E-6);
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber complex = ComplexNumber.fromMagnitudeAndAngle(5, 0);
		Assert.assertEquals(5, complex.getReal(), 1E-6);
		Assert.assertEquals(0, complex.getImaginary(), 1E-6);
		complex = ComplexNumber.fromMagnitudeAndAngle(5, Math.PI);
		Assert.assertEquals(-5, complex.getReal(), 1E-6);
		Assert.assertEquals(0, complex.getImaginary(), 1E-6);
		complex = ComplexNumber.fromMagnitudeAndAngle(10, Math.PI/4);
		Assert.assertEquals(10*Math.sqrt(2)/2, complex.getReal(), 1E-6);
		Assert.assertEquals(10*Math.sqrt(2)/2, complex.getImaginary(), 1E-6);
	}
	
	@Test
	public void getMagnitudeTest() {
		ComplexNumber complex = new ComplexNumber(3, -4.5);
		Assert.assertEquals(5.41, complex.getMagnitude(), 1e-2);
		complex = new ComplexNumber(1, 1);
		Assert.assertEquals(1.41, complex.getMagnitude(), 1e-2);
	}
	
	@Test
	public void getAngleTest() {
		ComplexNumber complex;
		complex = new ComplexNumber(1, 0);
		Assert.assertEquals(0, complex.getAngle(), 1e-6);
		complex = new ComplexNumber(0, 1);
		Assert.assertEquals(Math.PI/2, complex.getAngle(), 1e-6);
		complex = new ComplexNumber(-1, 0);
		Assert.assertEquals(Math.PI, complex.getAngle(), 1e-6);
		complex = new ComplexNumber(0, -1);
		Assert.assertEquals(3*Math.PI/2, complex.getAngle(), 1e-6);
		complex = new ComplexNumber(5, 0);
		Assert.assertEquals(0, complex.getAngle(), 1E-6);
		
		complex = new ComplexNumber(1, 1);
		Assert.assertEquals(0.79, complex.getAngle(), 1e-2);
		complex = new ComplexNumber(-3, 4.5);
		Assert.assertEquals(2.16, complex.getAngle(), 1e-2);
		complex = new ComplexNumber(-2, -4.6);
		Assert.assertEquals(4.30, complex.getAngle(), 1e-2);
		complex = new ComplexNumber(3, -4.5);
		Assert.assertEquals(5.3, complex.getAngle(), 1e-2);
	}
	
	@Test(expected=NullPointerException.class)
	public void addTestNull() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = null;
		c1.add(c2);
	}
	
	@Test
	public void addTest() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(3, -4.5);
		ComplexNumber c3 = new ComplexNumber(0, 0);
		ComplexNumber result = c1.add(c2.add(c3.add(new ComplexNumber(10, 0))));
		Assert.assertEquals(14, result.getReal(), 1E-6);
		Assert.assertEquals(-3.5, result.getImaginary(), 1E-6);
	}
	
	@Test(expected=NullPointerException.class)
	public void subTestNull() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = null;
		c1.sub(c2);
	}
	
	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(3, -4.5);
		ComplexNumber c3 = new ComplexNumber(0, 0);
		ComplexNumber result = c1.sub(c2.sub(c3.sub(new ComplexNumber(10, 0))));
		Assert.assertEquals(-12, result.getReal(), 1E-6);
		Assert.assertEquals(5.5, result.getImaginary(), 1E-6);
	}
	
	@Test(expected=NullPointerException.class)
	public void mulTestNull() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = null;
		c1.mul(c2);
	}
	
	@Test
	public void mulTest() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(3, -4.5);
		ComplexNumber c3 = new ComplexNumber(3.8, -4.2);
		ComplexNumber result = c1.mul(c2).mul(c3);
		Assert.assertEquals(22.2, result.getReal(), 1E-6);
		Assert.assertEquals(-37.2, result.getImaginary(), 1E-6);
		result = result.mul(new ComplexNumber(10, 0));
		Assert.assertEquals(222, result.getReal(), 1E-6);
		Assert.assertEquals(-372, result.getImaginary(), 1E-6);
	}
	
	@Test(expected=NullPointerException.class)
	public void divTestNull() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = null;
		c1.div(c2);
	}
	
	@Test
	public void divTest() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(3, -4.5);
		ComplexNumber c3 = new ComplexNumber(3.8, -4.2);
		ComplexNumber result = c1.div(c2);
		Assert.assertEquals(-0.051282, result.getReal(), 1E-6);
		Assert.assertEquals(0.256410, result.getImaginary(), 1E-6);
		result = result.div(c3);
		Assert.assertEquals(-0.039645, result.getReal(), 1E-6);
		Assert.assertEquals(0.023659, result.getImaginary(), 1E-6);
		result = result.div(new ComplexNumber(10, 0));
		Assert.assertEquals(-0.003964, result.getReal(), 1E-6);
		Assert.assertEquals(0.002366, result.getImaginary(), 1E-6);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void powerTestZero() {
		ComplexNumber c = new ComplexNumber(0, 0);
		c.power(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void powerTestLessThanZero() {
		ComplexNumber c = new ComplexNumber(0, 0);
		c.power(-1);
	}
	
	@Test
	public void powerTest() {
		ComplexNumber c = new ComplexNumber(2, 3);
		ComplexNumber result = c.power(2);
		Assert.assertEquals(-5, result.getReal(), 1E-6);
		Assert.assertEquals(12, result.getImaginary(), 1E-6);
		c = new ComplexNumber(3, -5);
		result = c.power(4);
		Assert.assertEquals(-644, result.getReal(), 1E-6);
		Assert.assertEquals(960, result.getImaginary(), 1E-6);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rootTestZero() {
		ComplexNumber c = new ComplexNumber(0, 0);
		c.root(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rootTestLessThanZero() {
		ComplexNumber c = new ComplexNumber(0, 0);
		c.root(-1);
	}
	
	@Test
	public void rootTest() {
		ComplexNumber c = new ComplexNumber(-3, 4.5);
		ComplexNumber[] complexArray = c.root(3);
		Assert.assertEquals(1.3201, complexArray[0].getReal(), 1E-4);
		Assert.assertEquals(1.1569, complexArray[0].getImaginary(), 1E-4);
		Assert.assertEquals(-1.66196, complexArray[1].getReal(), 1E-4);
		Assert.assertEquals(0.5648, complexArray[1].getImaginary(), 1E-4);
		Assert.assertEquals(0.3418, complexArray[2].getReal(), 1E-4);
		Assert.assertEquals(-1.7217, complexArray[2].getImaginary(), 1E-4);
	}
	
	@Test
	public void parseTest() {
		ComplexNumber c = ComplexNumber.parse("-3+4i");
		Assert.assertEquals(-3, c.getReal(), 1E-6);
		Assert.assertEquals(4, c.getImaginary(), 1E-6);
		c = ComplexNumber.parse("-3");
		Assert.assertEquals(-3, c.getReal(), 1E-6);
		Assert.assertEquals(0, c.getImaginary(), 1E-6);
		c = ComplexNumber.parse("0");
		Assert.assertEquals(0, c.getReal(), 1E-6);
		Assert.assertEquals(0, c.getImaginary(), 1E-6);
		c = ComplexNumber.parse("4i");
		Assert.assertEquals(0, c.getReal(), 1E-6);
		Assert.assertEquals(4, c.getImaginary(), 1E-6);
		c = ComplexNumber.parse("-4i");
		Assert.assertEquals(0, c.getReal(), 1E-6);
		Assert.assertEquals(-4, c.getImaginary(), 1E-6);
	}
	
	@Test
	public void toStringTest() {
		ComplexNumber c = ComplexNumber.parse("-3+4i");
		Assert.assertTrue(c.toString().equals("-3.0+4.0i"));
		c = ComplexNumber.parse("4i");
		Assert.assertTrue(c.toString().equals("4.0i"));
		c = ComplexNumber.parse("-3");
		Assert.assertTrue(c.toString().equals("-3.0"));
	}
}
