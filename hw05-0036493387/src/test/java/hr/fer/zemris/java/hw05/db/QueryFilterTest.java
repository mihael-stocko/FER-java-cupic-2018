package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;

public class QueryFilterTest {
	
	@Test
	public void testQueryFilter() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase studentDB = new StudentDatabase(lines);
		
		QueryParser parser = new QueryParser("lastName LIKE \"B*\"");
		List<ConditionalExpression> liszt = parser.getQuery();
		List<StudentRecord> filtered = studentDB.filter(new QueryFilter(liszt));
		
		Assert.assertEquals(4, filtered.size());
		Assert.assertEquals("0000000002", filtered.get(0).getJmbag());
		Assert.assertEquals("0000000003", filtered.get(1).getJmbag());
		Assert.assertEquals("0000000004", filtered.get(2).getJmbag());
		Assert.assertEquals("0000000005", filtered.get(3).getJmbag());
		
		parser = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
		liszt = parser.getQuery();
		filtered = studentDB.filter(new QueryFilter(liszt));
		
		Assert.assertEquals(1, filtered.size());
		Assert.assertEquals("0000000003", filtered.get(0).getJmbag());
	}
	
	@Test(expected=NullPointerException.class)
	public void testQueryFilterNull() {
		QueryFilter filter = new QueryFilter(null);
	}
}
