package hr.fer.zemris.java.hw05.db;

/**
 * This interface defines a single method used for testing if two strings satisfy a condition
 * defined by the class implementing this interface.
 * 
 * @author Mihael Stočko
 *
 */
public interface IComparisonOperator {

	/**
	 * Performs a test on the two strings provided and returns the result.
	 * 
	 * @param value1
	 * @param value2
	 * @return <code>true</code> if the two strings satisfy the condition, <code>false</code> otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
