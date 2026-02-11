package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command take one argument - a path to the new directory. It sets
 * the current directory to the one given, but stores the previous
 * directory onto the stack. 
 * 
 * @author Mihael Stočko
 *
 */
public class PushdCommand implements ShellCommand {

	/**
	 * A string literal used for mapping the stack in a map.
	 */
	public static final String cdstack = "cdstack";
	
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
		
		Path p = env.getCurrentDirectory().resolve(Paths.get(strings.get(0)));
		
		if(!p.toFile().isDirectory() || !p.toFile().exists()) {
			env.writeln("The given path is not an existing directory.");
			return ShellStatus.CONTINUE;
		}
		
		if(env.getSharedData(cdstack) == null) {
			env.setSharedData(cdstack, new Stack<Path>());
		}
		
		Stack<Path> stack = (Stack<Path>)env.getSharedData(cdstack);
		stack.push(env.getCurrentDirectory());
		env.setCurrentDirectory(p);
		env.writeln("Curent directory changed.");
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pushd";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command take one argument - a path to the new directory. It");
		list.add("sets the current directory to the one given, but stores the previous");
		list.add("directory on the stack.");
		
		return Collections.unmodifiableList(list);
	}
}
