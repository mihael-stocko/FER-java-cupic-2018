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
 * This command pops one path from the stack and, if the popped path
 * is a directory, sets the current directory to it. Takes no arguments.
 * 
 * @author Mihael Stočko
 *
 */
public class PopdCommand implements ShellCommand {

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
		
		if(env.getSharedData(cdstack) == null || ((Stack<Path>)env.getSharedData(cdstack)).isEmpty()) {
			env.writeln("There are no paths on the stack.");
			return ShellStatus.CONTINUE;
		}
		
		Path p = ((Stack<Path>)env.getSharedData(cdstack)).pop();
		if(!p.toFile().exists()) {
			env.writeln("The popped directory does not exist anymore.");
		} else {
			env.setCurrentDirectory(p);
			env.writeln("Done.");
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "popd";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command pops one path from the stack and, if the popped path");
		list.add("is a directory, sets the current directory to it.");
		list.add("Takes no arguments.");
		
		return Collections.unmodifiableList(list);
	}
}
