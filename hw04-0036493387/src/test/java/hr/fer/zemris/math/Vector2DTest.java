package hr.fer.zemris.math;

import org.junit.Test;
import org.junit.Assert;

public class Vector2DTest {

	@Test
	public void testConstructor() {
		Vector2D meinVektor = new Vector2D(2.3, 3.8);
		Assert.assertEquals(2.3, meinVektor.getX(), 1E-6);
		Assert.assertEquals(3.8, meinVektor.getY(), 1E-6);
	}
	
	@Test
	public void testTranslate() {
		Vector2D meinVektor = new Vector2D(2, 3);
		meinVektor.translate(new Vector2D(3.5, 2.8));
		Assert.assertEquals(5.5, meinVektor.getX(), 1E-6);
		Assert.assertEquals(5.8, meinVektor.getY(), 1E-6);
	}
	
	@Test
	public void testTranslated() {
		Vector2D meinVektor = new Vector2D(2, 3);
		Vector2D newVector = new Vector2D(3.5, 2.8);
		Vector2D translatedVector = meinVektor.translated(newVector);
		Assert.assertEquals(5.5, translatedVector.getX(), 1E-6);
		Assert.assertEquals(5.8, translatedVector.getY(), 1E-6);
	}
	
	@Test
	public void testRotate() {
		Vector2D meinVektor = new Vector2D(2, 0);
		meinVektor.rotate(90);
		Assert.assertEquals(0, meinVektor.getX(), 1E-6);
		Assert.assertEquals(2, meinVektor.getY(), 1E-6);
		meinVektor = new Vector2D(1, 1);
		meinVektor.rotate(90);
		Assert.assertEquals(-1, meinVektor.getX(), 1E-6);
		Assert.assertEquals(1, meinVektor.getY(), 1E-6);
		meinVektor = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/2);
		meinVektor.rotate(165);
		Assert.assertEquals(-Math.sqrt(3)/2, meinVektor.getX(), 1E-6);
		Assert.assertEquals(-1.0/2, meinVektor.getY(), 1E-6);
	}
	
	@Test
	public void testRotated() {
		Vector2D newVector;
		Vector2D meinVektor = new Vector2D(2, 0);
		newVector = meinVektor.rotated(90);
		Assert.assertEquals(0, newVector.getX(), 1E-6);
		Assert.assertEquals(2, newVector.getY(), 1E-6);
		meinVektor = new Vector2D(1, 1);
		newVector = meinVektor.rotated(90);
		Assert.assertEquals(-1, newVector.getX(), 1E-6);
		Assert.assertEquals(1, newVector.getY(), 1E-6);
		meinVektor = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/2);
		newVector = meinVektor.rotated(165);
		Assert.assertEquals(-Math.sqrt(3)/2, newVector.getX(), 1E-6);
		Assert.assertEquals(-1.0/2, newVector.getY(), 1E-6);
	}
	
	@Test
	public void testScale() {
		Vector2D meinVektor = new Vector2D(2, 0);
		meinVektor.scale(2);
		Assert.assertEquals(4, meinVektor.getX(), 1E-6);
		Assert.assertEquals(0, meinVektor.getY(), 1E-6);
		meinVektor = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/2);
		meinVektor.scale(-3);
		Assert.assertEquals(-Math.sqrt(2)/2 * 3 / Math.sqrt(2), meinVektor.getX(), 1E-6);
		Assert.assertEquals(-Math.sqrt(2)/2 * 3 / Math.sqrt(2), meinVektor.getY(), 1E-6);
	}
	
	@Test
	public void testScaled() {
		Vector2D newVector;
		Vector2D meinVektor = new Vector2D(2, 0);
		newVector = meinVektor.scaled(2);
		Assert.assertEquals(4, newVector.getX(), 1E-6);
		Assert.assertEquals(0, newVector.getY(), 1E-6);
		meinVektor = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/2);
		newVector = meinVektor.scaled(-3);
		Assert.assertEquals(-Math.sqrt(2)/2 * 3 / Math.sqrt(2), newVector.getX(), 1E-6);
		Assert.assertEquals(-Math.sqrt(2)/2 * 3 / Math.sqrt(2), newVector.getY(), 1E-6);
	}
	
	@Test
	public void testCopy() {
		Vector2D meinVektor = new Vector2D(5, 5);
		Vector2D copy = meinVektor.copy();
		Assert.assertEquals(5, copy.getX(), 1E-6);
		Assert.assertEquals(5, copy.getY(), 1E-6);
	}
}
