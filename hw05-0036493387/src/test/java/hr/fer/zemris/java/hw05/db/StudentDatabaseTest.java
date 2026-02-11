package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Assert;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class StudentDatabaseTest {

	@Test
	public void forJmbagTest() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch(Exception e) {}
		
		StudentDatabase studentDB = new StudentDatabase(lines);
		StudentRecord sr = new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		Assert.assertEquals(studentDB.forJMBAG("0000000002"), sr);
		sr = new StudentRecord("0000000010", "Dokleja", "Luka", 3);
		Assert.assertEquals(studentDB.forJMBAG("0000000010"), sr);
		sr = new StudentRecord("0000000050", "Sikirica", "Alen", 3);
		Assert.assertEquals(studentDB.forJMBAG("0000000050"), sr);
		sr = new StudentRecord("0000000034", "Majić", "Diana", 3);
		Assert.assertEquals(studentDB.forJMBAG("0000000034"), sr);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongInputFormatTest() {
		List<String> liszt = new LinkedList<>();
		liszt.add("94580495809 neko_prezime neko_ime");
		StudentDatabase sdb = new StudentDatabase(liszt);
	}
	
	@Test
	public void filterTest() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch(Exception e) {}
		
		StudentDatabase studentDB = new StudentDatabase(lines);
		
		List<StudentRecord> liszt = studentDB.filter(new IFilter() {
			@Override
			public boolean accepts(StudentRecord record) {
				return false;
			}
		});
		
		Assert.assertEquals(0, liszt.size());
		
		liszt = studentDB.filter(new IFilter() {
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		});
		
		Assert.assertEquals(63, liszt.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void filterNullTest() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch(Exception e) {}
		
		StudentDatabase studentDB = new StudentDatabase(lines);
		
		List<StudentRecord> liszt = studentDB.filter(null);
	}
}
