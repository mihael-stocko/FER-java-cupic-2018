package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * This interface defines methods that a shell command must have.
 * 
 * @author Mihael Stočko
 *
 */
public interface ShellCommand {

	/**
	 * Executes the command. Takes an environment and arguments for the command and
	 * returns a shell status.
	 * 
	 * @throws ShellIOException
	 */
	ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException;
	
	/**
	 * Returns the name of the command.
	 * 
	 * @return
	 */
	String getCommandName();
	
	/**
	 * Returns instructions for using the command.
	 * 
	 * @return
	 */
	List<String> getCommandDescription();
	
}
