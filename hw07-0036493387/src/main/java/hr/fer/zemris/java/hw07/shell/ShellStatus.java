package hr.fer.zemris.java.hw07.shell;

/**
 * This enumeration defines possible conclusions to command executions.
 * 
 * @author Mihael Stočko
 *
 */
public enum ShellStatus {
	/**
	 * If this is returned by a command, the shell continues to run.
	 */
	CONTINUE, 
	
	/**
	 * If this is returned by a command, the shell terminates itself.
	 */
	TERMINATE;
}
