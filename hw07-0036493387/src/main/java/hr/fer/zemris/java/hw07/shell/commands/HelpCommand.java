package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes zero or one arguments.
 * If zero arguments are provided, it lists all of the supported commands.
 * If a command name is given as an argument, it prints the description of the
 * selected command.
 * 
 * @author Mihael Stočko
 *
 */
public class HelpCommand implements ShellCommand {

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
		
		if(strings.size() != 0 && strings.size() != 1) {
			env.writeln("The command " + getCommandName() + " expects zero or one arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if(strings.size() == 0) {
			for(Map.Entry<String, ShellCommand> entry : env.commands().entrySet()) {
				env.writeln(entry.getKey());
			}
		} else {
			ShellCommand command = env.commands().get(strings.get(0));
			if(command == null) {
				env.writeln("The requested command does not exist.");
				return ShellStatus.CONTINUE;
			}
			List<String> lines = command.getCommandDescription();
			for(String line : lines) {
				env.writeln(line);
			}
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes zero or one arguments.");
		list.add("If zero arguments are provided, it lists all of the supported commands.");
		list.add("If a command name is given as an argument, it prints the description of the");
		list.add("selected command.");
		
		return Collections.unmodifiableList(list);
	}
}
