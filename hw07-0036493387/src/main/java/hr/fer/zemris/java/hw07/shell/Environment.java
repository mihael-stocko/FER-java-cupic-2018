package hr.fer.zemris.java.hw07.shell;

import java.util.SortedMap;

/**
 * This interface defines methods that an environment must have.
 * 
 * @author Mihael Stočko
 *
 */
public interface Environment {
	/**
	 * Reads a line from the standard input and returns it.
	 * 
	 * @return The string that has been read.
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes the provided string to the standard output.
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes the provided string to the standard output and starts a new line.
	 * 
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns a map of all registered commands.
	 * @return A map of commands.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Getter for MULTILINE symbol
	 * @return
	 */
	Character getMultilineSymbol();
	
	/**
	 * Setter for MULTILINE symbol
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Getter for PROMPT symbol
	 * @return
	 */
	Character getPromptSymbol();
	
	/**
	 * Setter for PROMPT symbol
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Getter for MORELINES symbol
	 * @return
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Setter for MORELINES symbol
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
