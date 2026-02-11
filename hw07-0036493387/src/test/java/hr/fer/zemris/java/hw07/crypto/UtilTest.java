package hr.fer.zemris.java.hw07.crypto;

import org.junit.Test;
import org.junit.Assert;

public class UtilTest {

	@Test(expected=NullPointerException.class)
	public void testHextobyteNull() {
		Util.hextobyte(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHextobyteWrongInputUnsupportedChar() {
		String s = "01ah22";
		Util.hextobyte(s);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHextobyteWrongInputOddLength() {
		String s = "01a";
		Util.hextobyte(s);
	}
	
	@Test
	public void testHextobyte() {
		String s = "01aE22";
		byte[] b = Util.hextobyte(s);
		Assert.assertEquals(1, b[0]);
		Assert.assertEquals(-82, b[1]);
		Assert.assertEquals(34, b[2]);
	}
	
	@Test
	public void testHextobyteLong() {
		String s = "01aE2230abE452";
		byte[] b = Util.hextobyte(s);
		Assert.assertEquals(1, b[0]);
		Assert.assertEquals(-82, b[1]);
		Assert.assertEquals(34, b[2]);
		Assert.assertEquals(48, b[3]);
		Assert.assertEquals(-85, b[4]);
		Assert.assertEquals(-28, b[5]);
		Assert.assertEquals(82, b[6]);
	}
	
	@Test(expected=NullPointerException.class)
	public void testBytetohexNull() {
		Util.bytetohex(null);
	}
	
	@Test
	public void testBytetohexEmpty() {
		byte[] bytes = new byte[0];
		String s = Util.bytetohex(bytes);
		Assert.assertEquals("", s);
	}
	
	@Test
	public void testBytetohex() {
		byte[] bytes = new byte[] {1, -82, 34};
		String s = Util.bytetohex(bytes);
		Assert.assertEquals(s, "01ae22");
	}
	
	@Test
	public void testBytetohexLong() {
		byte[] bytes = new byte[] {1, -82, 34, 48, -85, -28, 82};
		String s = Util.bytetohex(bytes);
		Assert.assertEquals(s, "01ae2230abe452");
	}
}
