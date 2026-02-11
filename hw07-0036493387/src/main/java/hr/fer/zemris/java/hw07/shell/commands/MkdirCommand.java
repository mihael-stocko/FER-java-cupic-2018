package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes one argument - a path to a directory.
 * It then creates the given directory.
 * 
 * @author Mihael Stočko
 *
 */
public class MkdirCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		List<String> strings = null;
		try {
			strings = Util.split(arguments);
		} catch(IllegalArgumentException e) {
			env.writeln("The string under the quotes is not in the required format. " + e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(strings.size() != 1) {
			env.writeln("The command " + getCommandName() + " expects one argument.");
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(strings.get(0));
		
		try {
			Files.createDirectories(p);
		} catch(IOException e) {
			env.writeln("Cannot create the requested directory structure.");
		}
		
		env.writeln("Done.");
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes one argument - a path to a directory.");
		list.add("It then creates the given directory.");
		
		return Collections.unmodifiableList(list);
	}
}
