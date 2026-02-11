package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class ArrayIndexedCollectionTest {
	
	@Test
	public void addTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		Assert.assertEquals(16, arrayCol.getCapactiy());
		arrayCol.add(5);
		arrayCol.add(1);
		arrayCol.add("Dnjepar");
		arrayCol.add(0);
		Assert.assertEquals(1, arrayCol.get(1));
		Assert.assertEquals(0, arrayCol.get(3));
	}
	
	@Test(expected=NullPointerException.class)
	public void addNullTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(null);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void getIndexOverTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(5);
		arrayCol.add(1);
		arrayCol.add("Dnjepar");
		arrayCol.add(0);
		arrayCol.get(10);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getIndexUnderTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(5);
		arrayCol.add(1);
		arrayCol.add("Dnjepar");
		arrayCol.add(0);
		arrayCol.get(-1);
	}
	
	@Test
	public void sizeTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add("Vepar");
		arrayCol.add("Nepar");
		arrayCol.add("Dnjepar");
		arrayCol.add(0);
		Assert.assertEquals(4, arrayCol.size());
	}
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add("Vepar");
		arrayCol.add("Nepar");
		arrayCol.add("Dnjepar");
		arrayCol.add(0);
		Assert.assertEquals(4, arrayCol.size());
		arrayCol.clear();
		Assert.assertEquals(0, arrayCol.size());
	}
	
	@Test
	public void insertTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		arrayCol.insert(0, 2);
		Assert.assertEquals(1, arrayCol.get(0));
		Assert.assertEquals(2, arrayCol.get(1));
		Assert.assertEquals(0, arrayCol.get(2));
		Assert.assertEquals(3, arrayCol.get(3));
		Assert.assertEquals(4, arrayCol.get(4));
	}
	
	@Test(expected=NullPointerException.class)
	public void insertNullTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		arrayCol.insert(null, 2);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void insertBoundsUnderTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		arrayCol.insert(0, -1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void insertBoundsOverTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		arrayCol.insert(null, 30);
	}
	
	@Test
	public void indexOfTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		Assert.assertEquals(-1, arrayCol.indexOf(0));
		Assert.assertEquals(0, arrayCol.indexOf(1));
		Assert.assertEquals(3, arrayCol.indexOf(4));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void removeIndexTooSmallTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		arrayCol.remove(-1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void removeIndexTooLargeTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		arrayCol.remove(4);
	}
	
	@Test
	public void removeTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		Assert.assertEquals(4, arrayCol.size());
		arrayCol.remove(1);
		Assert.assertEquals(1, arrayCol.get(0));
		Assert.assertEquals(3, arrayCol.get(1));
		Assert.assertEquals(4, arrayCol.get(2));
		Assert.assertEquals(3, arrayCol.size());
	}
	
	@Test
	public void getCapacityTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		Assert.assertEquals(16, arrayCol.getCapactiy());
	}
	
	@Test
	public void containsTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		Assert.assertFalse(arrayCol.contains(0));
		Assert.assertTrue(arrayCol.contains(1));
		Assert.assertTrue(arrayCol.contains(2));
	}
	
	@Test(expected=NullPointerException.class)
	public void forEachNullTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		arrayCol.forEach(null);
	}
	
	@Test
	public void forEachTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		
		ArrayIndexedCollection temp = new ArrayIndexedCollection();
		arrayCol.forEach(new Processor() {
			@Override
			public void process(Object value) {
				temp.add(value);
			}
		});
		
		for(int i = 0; i < 4; ++i) {
			Assert.assertEquals(arrayCol.get(i), temp.get(i));
		}
	}
	
	@Test
	public void toArrayTest() {
		ArrayIndexedCollection arrayCol = new ArrayIndexedCollection();
		arrayCol.add(1);
		arrayCol.add(2);
		arrayCol.add(3);
		arrayCol.add(4);
		
		Object[] newArray = arrayCol.toArray();
		
		for(int i = 0; i < 4; ++i) {
			Assert.assertEquals(arrayCol.get(i), newArray[i]);
		}
	}
}