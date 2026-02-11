package hr.fer.zemris.java.hw05.db;

/**
 * This interface defines a single method used determining whether a StudentRecord will be accepted.
 * 
 * @author Mihael Stočko
 *
 */
public interface IFilter {
	
	/**
	 * Determines whether a StudentRecord will be accepted
	 * 
	 * @param record {@link StudentRecord} to be tested
	 * @return <code>true</code> if the record has passed the test, <code>false</code> otherwise.
	 */
	 public boolean accepts(StudentRecord record);
}