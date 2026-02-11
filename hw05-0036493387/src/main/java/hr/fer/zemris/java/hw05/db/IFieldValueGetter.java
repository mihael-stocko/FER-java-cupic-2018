package hr.fer.zemris.java.hw05.db;

/**
 * This interface defines a single method used for getting a certain field from a {@link StudentRecord}.
 * 
 * @author Mihael Stočko
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * This method must return one field from the class {@link StudentRecord}.
	 * 
	 * @param record
	 * @return
	 */
	public String get(StudentRecord record);
}
