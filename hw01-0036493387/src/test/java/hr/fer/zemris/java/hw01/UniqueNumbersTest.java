package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Assert;

public class UniqueNumbersTest {
	
	@Test
	public void dodavanjeIspravno() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		
		Assert.assertEquals(42, glava.value);
		Assert.assertEquals(21, glava.left.value);
		Assert.assertEquals(35, glava.left.right.value);
		Assert.assertEquals(76, glava.right.value);
		
		Assert.assertEquals(null, glava.right.right);
		Assert.assertEquals(null, glava.left.right.left);
	}
	
	@Test
	public void treeSizePrazno() {
		UniqueNumbers.TreeNode glava = null;
		Assert.assertEquals(0, UniqueNumbers.treeSize(glava));
	}
	
	@Test
	public void treeSizeNeprazno() {
		UniqueNumbers.TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		Assert.assertEquals(2, UniqueNumbers.treeSize(glava));
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		Assert.assertEquals(4, UniqueNumbers.treeSize(glava));
	}
	
	@Test
	public void containsValueTest() {
		UniqueNumbers.TreeNode glava = null;
		Assert.assertFalse(UniqueNumbers.containsValue(glava, 42));
		glava = UniqueNumbers.addNode(glava, 42);
		Assert.assertTrue(UniqueNumbers.containsValue(glava, 42));
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 100);
		Assert.assertTrue(UniqueNumbers.containsValue(glava, 100));
	}
}
