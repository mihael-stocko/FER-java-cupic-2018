package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;
import org.junit.Assert;

public class ObjectMultistackTest {

	public ObjectMultistack stack = new ObjectMultistack();
	
	@Test(expected=NullPointerException.class)
	public void testPushNull() {
		ValueWrapper wrapper = new ValueWrapper(5);
		stack.push(null, wrapper);
	}
	
	@Test
	public void testPushOne() {
		ValueWrapper wrapper = new ValueWrapper(5);
		stack.push("first", wrapper);
		Assert.assertEquals(5, stack.peek("first").getValue());
	}
	
	@Test
	public void testPushMultiple() {
		ValueWrapper wrapper1 = new ValueWrapper(5);
		ValueWrapper wrapper2 = new ValueWrapper(6);
		ValueWrapper wrapper3 = new ValueWrapper(7);
		ValueWrapper wrapper4 = new ValueWrapper(8);
		ValueWrapper wrapper5 = new ValueWrapper(9);
		
		stack.push("first", wrapper1);
		stack.push("second", wrapper2);
		stack.push("third", wrapper3);
		stack.push("third", wrapper4);
		stack.push("third", wrapper5);
		
		Assert.assertEquals(5, stack.peek("first").getValue());
		Assert.assertEquals(6, stack.peek("second").getValue());
		Assert.assertEquals(9, stack.peek("third").getValue());
		stack.pop("third");
		Assert.assertEquals(8, stack.peek("third").getValue());
		stack.pop("third");
		Assert.assertEquals(7, stack.peek("third").getValue());
	}
	
	@Test
	public void testIsEmpty() {
		ValueWrapper wrapper1 = new ValueWrapper(5);
		Assert.assertTrue(stack.isEmpty("first"));
		stack.push("first", wrapper1);
		Assert.assertFalse(stack.isEmpty("first"));
		stack.pop("first");
		Assert.assertTrue(stack.isEmpty("first"));
	}
	
	@Test
	public void testPopOne() {
		ValueWrapper wrapper1 = new ValueWrapper(5);
		stack.push("first", wrapper1);
		Assert.assertEquals(5, stack.peek("first").getValue());
		stack.pop("first");
		Assert.assertTrue(stack.isEmpty("first"));
	}
	
	@Test
	public void testPopMultiple() {
		ValueWrapper wrapper1 = new ValueWrapper(5);
		ValueWrapper wrapper2 = new ValueWrapper(6);
		ValueWrapper wrapper3 = new ValueWrapper(7);
		
		stack.push("first", wrapper1);
		stack.push("first", wrapper2);
		stack.push("first", wrapper3);
		
		stack.pop("first");
		stack.pop("first");
		stack.pop("first");
		
		Assert.assertTrue(stack.isEmpty("first"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testPopNull() {
		stack.pop(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPopNonExistingKey() {
		ValueWrapper wrapper1 = new ValueWrapper(5);
		stack.push("first", wrapper1);
		stack.pop("second");
	}
	
	@Test(expected=NullPointerException.class)
	public void testPeekNull() {
		stack.peek(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPeekNonExistingKey() {
		ValueWrapper wrapper1 = new ValueWrapper(5);
		stack.push("first", wrapper1);
		stack.peek("second");
	}
 }
