package hr.fer.zemris.java.gui.prim;

import org.junit.Assert;
import org.junit.Test;

public class PrimDemoTest {

	public static final double delta = 1e-6;
	
	@Test
	public void testNext() {
		PrimDemo.PrimListModel model = new PrimDemo.PrimListModel();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		Assert.assertEquals(1, model.getElementAt(0), delta);
		Assert.assertEquals(2, model.getElementAt(1), delta);
		Assert.assertEquals(3, model.getElementAt(2), delta);
		Assert.assertEquals(5, model.getElementAt(3), delta);
		Assert.assertEquals(7, model.getElementAt(4), delta);
		Assert.assertEquals(11, model.getElementAt(5), delta);
		Assert.assertEquals(13, model.getElementAt(6), delta);
	}
	
	@Test
	public void testSize() {
		PrimDemo.PrimListModel model = new PrimDemo.PrimListModel();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		
		Assert.assertEquals(7, model.getSize(), delta);
	}
}
