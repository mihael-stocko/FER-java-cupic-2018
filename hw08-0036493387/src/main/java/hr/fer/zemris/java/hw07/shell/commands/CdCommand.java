package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command changes the current directory of the shell. It takes one 
 * argument - a path to the new directory. If the given directory does not exist,
 * an error message is written.
 * 
 * @author Mihael Stočko
 *
 */
public class CdCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		List<String> strings = Util.splitArgs(env, arguments, getCommandName(), 1, false);
		if(strings == null) {
			return ShellStatus.CONTINUE;
		}
		
		try {
			env.setCurrentDirectory(env.getCurrentDirectory().resolve(Paths.get(strings.get(0))));
			env.writeln("Done.");
		} catch(NullPointerException e) {
			env.writeln(e.getMessage());
		} catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cd";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command changes the current directory of the shell. It takes one");
		list.add("argument - a path to the new directory.");
		
		return Collections.unmodifiableList(list);
	}
}
