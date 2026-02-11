package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Assert;

public class FactorialTest {
	
	@Test
	public void faktorijelaUnutarRaspona() {
		Assert.assertEquals(120, Factorial.faktorijela(5));
	}
	
	@Test
	public void faktorijelaNula() {
		Assert.assertEquals(1, Factorial.faktorijela(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void faktorijelaNegativanArgument() {
		Factorial.faktorijela(-1);
	}
}
