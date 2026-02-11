package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class FieldValueGettersTest {

	@Test
	public void testName() {
		StudentRecord sr = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		Assert.assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(sr));
	}
	
	@Test(expected=NullPointerException.class)
	public void testNameNull() {
		StudentRecord sr = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		Assert.assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(null));
	}
	
	@Test
	public void testSurname() {
		StudentRecord sr = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		Assert.assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(sr));
	}
	
	@Test(expected=NullPointerException.class)
	public void testSurnameNull() {
		StudentRecord sr = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		Assert.assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(null));
	}
	
	@Test
	public void testJmbag() {
		StudentRecord sr = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		Assert.assertEquals("0000000001", FieldValueGetters.JMBAG.get(sr));
	}
	
	@Test(expected=NullPointerException.class)
	public void testJmbagNull() {
		StudentRecord sr = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		Assert.assertEquals("0000000001", FieldValueGetters.JMBAG.get(null));
	}
}
