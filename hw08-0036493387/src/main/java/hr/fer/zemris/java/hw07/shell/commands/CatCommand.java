package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
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
 * This command takes one mandatory and one optional argument.
 * The first argument is the path to a file.
 * The second one is a charset.
 * The command reads all of the content from the provided file
 * and prints it out.
 * 
 * @author Mihael Stočko
 *
 */
public class CatCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		List<String> strings = Util.splitArgs(env, arguments, getCommandName(), 1, true);
		if(strings == null) {
			return ShellStatus.CONTINUE;
		}
		
		Charset c = null;
		
		if(strings.size() == 1) {
			c = Charset.defaultCharset();
		} else if(strings.size() == 2) {
			try {
				c = Charset.forName(strings.get(1));
			} catch(Exception e) {
				env.writeln("The given charset is not supported.");
				return ShellStatus.CONTINUE;
			}
		}
		
		Path p = env.getCurrentDirectory().resolve(Paths.get(strings.get(0)));
		if(!p.toFile().exists()) {
			env.writeln("The given file does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		try(BufferedReader is = Files.newBufferedReader(p, c)) {
			while(true) {
				String line = is.readLine();
				if(line == null) {
					break;
				}
				env.writeln(line);
			}
		} catch(IOException e) {
			env.writeln("Cannot read from the provided file.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes one mandatory and one optional argument.");
		list.add("The first argument is the path to a file.");
		list.add("The second one is a charset.");
		list.add("The command reads all of the content from the provided file");
		list.add("and prints it out.");
		
		return Collections.unmodifiableList(list);
	}
}
