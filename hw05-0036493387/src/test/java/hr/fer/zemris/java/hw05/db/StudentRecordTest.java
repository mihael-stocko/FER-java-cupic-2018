package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class StudentRecordTest {

	@Test
	public void equalsTest() {
		StudentRecord sr1 = new StudentRecord("0000000000", "Čupić", "Marko", 6);
		StudentRecord sr2 = new StudentRecord("0000000000", "Cupic", "Marko", 6);
		StudentRecord sr3 = new StudentRecord("0000000001", "Mornar", "Vedran", 18);
		
		Assert.assertFalse(sr1.equals(sr3));
		Assert.assertTrue(sr1.equals(sr2));
	}
}
