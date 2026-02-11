package hr.fer.zemris.lsystems.impl;

import org.junit.Test;
import org.junit.Assert;
import hr.fer.zemris.math.Vector2D;
import java.awt.Color;

public class TurtleStateTest {

	@Test(expected=NullPointerException.class)
	public void testPositionNull() {
		TurtleState leonardo = new TurtleState(null, new Vector2D(1, 2), Color.BLACK, 1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testAngleNull() {
		TurtleState michelangelo = new TurtleState(new Vector2D(1, 2), null, Color.BLACK, 1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testColorNull() {
		TurtleState donatello = new TurtleState(new Vector2D(1, 2), new Vector2D(1, 2), null, 1);
	}
	
	@Test
	public void testConstructorAndGetters() {
		TurtleState raphael = new TurtleState(new Vector2D(1, 2), new Vector2D(3, 4),
				Color.black, 13);
		Assert.assertEquals(1, raphael.getPosition().getX(), 1E-6);
		Assert.assertEquals(2, raphael.getPosition().getY(), 1E-6);
		Assert.assertEquals(Color.black, raphael.getColor());
		Assert.assertEquals(13, raphael.getLength(), 1E-6);
	}
}
