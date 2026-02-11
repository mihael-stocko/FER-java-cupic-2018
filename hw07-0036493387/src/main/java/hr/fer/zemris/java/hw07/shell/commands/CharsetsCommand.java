package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes no arguments and lists all of the supported charsets.
 * 
 * @author Mihael Stočko
 *
 */
public class CharsetsCommand implements ShellCommand {

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
		if(strings.size() != 0) {
			env.writeln("The command " + getCommandName() + " expects zero arguments.");
			return ShellStatus.CONTINUE;
		}
		
		SortedMap<String, Charset> charsets = new TreeMap<>();
		charsets = Charset.availableCharsets();
		
		for(Map.Entry<String, Charset> entry : charsets.entrySet()) {
			env.writeln(entry.getValue().toString());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes no arguments and lists all of the supported charsets.");
		
		return Collections.unmodifiableList(list);
	}
}
