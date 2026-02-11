package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.LinkedList;
import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * This class models a simple student database
 * 
 * @author Mihael Stočko
 *
 */
public class StudentDatabase {

	/**
	 * A list of student records
	 */
	public List<StudentRecord> list = new LinkedList<>();
	
	/**
	 * A hashtable for fast retrieval
	 */
	public SimpleHashtable<String, StudentRecord> map = new SimpleHashtable<>();
	
	/**
	 * Constructor
	 * 
	 * @param lines
	 */
	public StudentDatabase(List<String> lines) {
		int i = 0;
		for(String line : lines) {
			String[] arguments = line.split("\\s+");
			String name = null;
			String surname = null; 
			String jmbag = null;
			int finalGrade = 0;
			try {
				if(arguments.length == 4) {
					jmbag = arguments[0];
					surname = arguments[1];
					name = arguments[2];
					finalGrade = Integer.parseInt(arguments[3]);
				} else if(arguments.length == 5) {
					jmbag = arguments[0];
					surname = arguments[1] + " " + arguments[2];
					name = arguments[3];
					finalGrade = Integer.parseInt(arguments[4]);
				} else {
					throw new IllegalArgumentException("Wrong number of arguments for the student record.");
				}
			} catch(Exception e) {
				throw new IllegalArgumentException("Format of student record is not valid.");
			}
			StudentRecord sr = new StudentRecord(jmbag, surname, name, finalGrade);
			list.add(sr);
			map.put(jmbag, list.get(i));
			i++;
		}
	}
	
	/**
	 * Returns a student record for the provided jmbag in O(1)
	 * 
	 * @param jmbag
	 * @return
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return map.get(jmbag);
	}
	
	/**
	 * Filters the database using the provided filter and returns the filtered list of student records.
	 * 
	 * @param filter
	 * @return
	 */
	public List<StudentRecord> filter(IFilter filter) {
		if(filter == null) {
			throw new IllegalArgumentException("Filter cannot be null.");
		}
		List<StudentRecord> tempList = new LinkedList<>();
		for(StudentRecord sr : list) {
			if(filter.accepts(sr)) {
				tempList.add(sr);
			}
		}
		return tempList;
	}
}
