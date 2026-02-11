package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class DictionaryTest {

	@Test
	public void testIsEmpty() {
		Dictionary jisho = new Dictionary();
		Assert.assertTrue(jisho.isEmpty());
	}
	
	@Test
	public void testSize() {
		Dictionary jisho = new Dictionary();
		Assert.assertEquals(0, jisho.size());
		jisho.put(0, "Prvi string");
		jisho.put(1, "Osmi string");
		jisho.put(2, "Ne znam više koji string");
		Assert.assertEquals(3, jisho.size());
	}
	
	@Test
	public void testClear() {
		Dictionary jisho = new Dictionary();
		Assert.assertEquals(0, jisho.size());
		jisho.put(0, "Prvi string");
		jisho.put(1, "Osmi string");
		jisho.put(2, "Ne znam više koji string");
		Assert.assertEquals(3, jisho.size());
		jisho.clear();
		Assert.assertEquals(0, jisho.size());
	}
	
	@Test
	public void testPutGet() {
		Dictionary jisho = new Dictionary();
		jisho.put(1685, "Bach");
		jisho.put(1756, "Mozart");
		jisho.put(1810, "Chopin");
		jisho.put(1873, "Rachmaninoff");
		jisho.put(1770, "Beethoven");
		jisho.put(1986, "Lady Gaga");
		jisho.put(1811, "Liszt");
		jisho.put(1862, "Debussy");
		Assert.assertEquals("Chopin", jisho.get(1810));
		Assert.assertEquals("Bach", jisho.get(1685));
		Assert.assertEquals("Beethoven", jisho.get(1770));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetNull() {
		Dictionary jisho = new Dictionary();
		jisho.put(1, "Jedan");
		jisho.put(3, "Tri");
		jisho.put(6, "Osam");
		jisho.put(10, "Deset");
		jisho.get(13);
	}
	
	@Test(expected=NullPointerException.class)
	public void testPutNull() {
		Dictionary jisho = new Dictionary();
		jisho.put(null, "SaRmA");
	}
}
