package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector3Test {

	public static final double epsilon = 1e-6;
	
	@Test
	public void testNorm() {
		Vector3 vector = new Vector3(2, 4, -8);
		Assert.assertEquals(9.165151, vector.norm(), epsilon);
	}
	
	@Test
	public void testNormalized() {
		Vector3 vector = new Vector3(10, 40, -20);
		Vector3 vector1 = vector.normalized();
		Assert.assertEquals(0.218218, vector1.getX(), epsilon);
		Assert.assertEquals(0.872872, vector1.getY(), epsilon);
		Assert.assertEquals(-0.436436, vector1.getZ(), epsilon);
	}
	
	@Test
	public void testAdd() {
		Vector3 v1 = new Vector3(2, -5, 3);
		Vector3 v2 = new Vector3(1, 3, -2);
		Vector3 v3 = v1.add(v2);
		Assert.assertEquals(3, v3.getX(), epsilon);
		Assert.assertEquals(-2, v3.getY(), epsilon);
		Assert.assertEquals(1, v3.getZ(), epsilon);
	}
	
	@Test
	public void testSub() {
		Vector3 v1 = new Vector3(2, -5, 3);
		Vector3 v2 = new Vector3(1, 3, -2);
		Vector3 v3 = v1.sub(v2);
		Assert.assertEquals(1, v3.getX(), epsilon);
		Assert.assertEquals(-8, v3.getY(), epsilon);
		Assert.assertEquals(5, v3.getZ(), epsilon);
	}
	
	@Test
	public void testDot() {
		Vector3 v1 = new Vector3(2, -5, 3);
		Vector3 v2 = new Vector3(1, 3, -2);
		Assert.assertEquals(-19, v1.dot(v2), epsilon);
	}
	
	@Test
	public void testCross() {
		Vector3 v1 = new Vector3(2, -5, 3);
		Vector3 v2 = new Vector3(1, 3, -2);
		Vector3 v3 = v1.cross(v2);
		Assert.assertEquals(1, v3.getX(), epsilon);
		Assert.assertEquals(7, v3.getY(), epsilon);
		Assert.assertEquals(11, v3.getZ(), epsilon);
	}
	
	@Test
	public void testScale() {
		Vector3 v = new Vector3(5, 8, -1);
		Vector3 v1 = v.scale(3);
		Assert.assertEquals(15, v1.getX(), epsilon);
		Assert.assertEquals(24, v1.getY(), epsilon);
		Assert.assertEquals(-3, v1.getZ(), epsilon);
	}
	
	@Test
	public void testCosAngle() {
		Vector3 v1 = new Vector3(2, -5, 3);
		Vector3 v2 = new Vector3(1, 3, -2);
		Assert.assertEquals(-0.823754, v1.cosAngle(v2), epsilon);
	}
}
