package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Assert;

public class RectangleTest {

	@Test
	public void povrsinaIspravniArgumenti() {
		Assert.assertEquals(20, Rectangle.povrsina(5, 4), 1E-6);
		Assert.assertEquals(20, Rectangle.povrsina(5.0, 4.0), 1E-6);
		Assert.assertEquals(25.92, Rectangle.povrsina(5.4, 4.8), 1E-6);
	}
	
	@Test
	public void opsegIspravniArgumenti() {
		Assert.assertEquals(18, Rectangle.opseg(5, 4), 1E-6);
		Assert.assertEquals(18, Rectangle.opseg(5.0, 4.0), 1E-6);
		Assert.assertEquals(20.4, Rectangle.opseg(5.4, 4.8), 1E-6);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void povrsinaNeispravanArgument() {
		Rectangle.povrsina(5, 0);
		Rectangle.povrsina(0, 5);
		Rectangle.povrsina(0, 0);
		Rectangle.povrsina(5, -1);
		Rectangle.povrsina(-1, 5);
		Rectangle.povrsina(-1, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void opsegNeispravanArgument() {
		Rectangle.opseg(5, 0);
		Rectangle.opseg(0, 5);
		Rectangle.opseg(0, 0);
		Rectangle.opseg(5, -1);
		Rectangle.opseg(-1, 5);
		Rectangle.opseg(-1, -1);
	}
}
