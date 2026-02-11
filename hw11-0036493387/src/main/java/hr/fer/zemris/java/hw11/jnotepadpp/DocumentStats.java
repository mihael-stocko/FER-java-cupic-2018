package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * A collection of some information about a document
 * 
 * @author Mihael Stočko
 *
 */
public class DocumentStats {

	/**
	 * Number of characters
	 */
	private int characters = 0;
	
	/**
	 * Number of non-blank characters
	 */
	private int nonBlankCharacters = 0;
	
	/**
	 * Number of lines
	 */
	private int lines = 0;
	
	/**
	 * Getter for characters
	 */
	public int getCharacters() {
		return characters;
	}
	
	/**
	 * Getter for nonBlankCharacters
	 */
	public int getNonBlankCharacters() {
		return nonBlankCharacters;
	}
	
	/**
	 * Getter for lines
	 */
	public int getLines() {
		return lines;
	}
	
	/**
	 * Increments characters by one.
	 */
	public void incrementCharacters() {
		characters++;
	}
	
	/**
	 * Increments nonBlankCharacters by one.
	 */
	public void incrementNonBlankCharacters() {
		nonBlankCharacters++;
	}
	
	/**
	 * Increments lines by one.
	 */
	public void incrementLines() {
		lines++;
	}
}
