package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes no arguments and is used to terminate the shell.
 * 
 * @author Mihael Stočko
 *
 */
public class ExitCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		if(arguments.length() != 0) {
			env.writeln("The command " + getCommandName() + " expects zero arguments.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "exit";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes no arguments and is used to terminate the shell.");
		
		return Collections.unmodifiableList(list);
	}
}
