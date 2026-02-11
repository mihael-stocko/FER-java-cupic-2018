package hr.fer.zemris.java.hw07.shell.commands;

/**
 * This interface is used for creating and retrieving the new name for a file.
 * 
 * @author Mihael Stočko
 *
 */
public interface NameBuilderInfo {
	
	/**
	 * Returns a {@link StringBuilder} object that has been built until the point of calling.
	 * 
	 * @return A {@link StringBuilder} object
	 */
	StringBuilder getStringBuilder();
	
	/**
	 * For the given index, returns what the respective group contains.
	 * 
	 * @param index The index of the group
	 * @return The content of the group
	 */
	String getGroup(int index);
}
