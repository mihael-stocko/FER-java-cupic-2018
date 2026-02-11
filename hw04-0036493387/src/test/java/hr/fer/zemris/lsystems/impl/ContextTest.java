package hr.fer.zemris.lsystems.impl;

import org.junit.Test;
import org.junit.Assert;
import hr.fer.zemris.math.Vector2D;
import java.awt.Color;

public class ContextTest {
	
	@Test(expected=UnsupportedOperationException.class)
	public void testGetEmpty() {
		Context context = new Context();
		context.getCurrentState();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testPopEmpty() {
		Context context = new Context();
		context.popState();
	}
	
	@Test(expected=NullPointerException.class)
	public void testPushNull() {
		Context context = new Context();
		context.pushState(null);
	}
	
	@Test
	public void testPush() {
		Context context = new Context();
		context.pushState(new TurtleState(new Vector2D(1, 2), new Vector2D(20, 30), Color.BLACK, 10));
		Assert.assertEquals(10, context.getCurrentState().getLength(), 1E-6);
	}
}
