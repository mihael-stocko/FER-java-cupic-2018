package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes no arguments. It lists all of the paths currently on the stack.
 * 
 * @author Mihael Stočko
 *
 */
public class ListdCommand implements ShellCommand {

	/**
	 * A string literal used for mapping the stack in a map.
	 */
	public static final String cdstack = "cdstack";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		if(arguments.length() != 0) {
			env.writeln("The command " + getCommandName() + " expects zero arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Stack<Path> stack = ((Stack<Path>)env.getSharedData(cdstack));
		if(stack.size() == 0) {
			env.writeln("There are no paths on the stack.");
			return ShellStatus.CONTINUE;
		}
		
		for(Path path : stack) {
			env.writeln(path.toString());
		}
		
		return ShellStatus.CONTINUE	;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "listd";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes no arguments. It lists all of the paths currently on the stack.");
		
		return Collections.unmodifiableList(list);
	}
}
