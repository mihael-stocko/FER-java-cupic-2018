package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command is used to modify the symbols that denote
 * prompt, multiline prompt and line break.
 * It takes either one or two arguments. If only one argument is
 * provided, it has to be PROMPT, MULTILINE or MORELINES.
 * The output will be what the requested symbol is currently set to.
 * If two arguments are given, the first must be the name of the symbol,
 * the other one being a character.
 * The symbol is then changed to the character given.
 * 
 * @author Mihael Stočko
 *
 */
public class SymbolCommand implements ShellCommand {
	
	/**
	 * A keyword representing the PROMPT symbol
	 */
	private final String PROMPT = "PROMPT";
	
	/**
	 * A keyword representing the MULTILINE symbol
	 */
	private final String MULTILINE = "MULTILINE";
	
	/**
	 * A keyword representing the MORELINES symbol
	 */
	private final String MORELINES = "MORELINES";
	
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
		
		if(strings.size() == 1) {
			env.write("The symbol for ");
			env.write(strings.get(0));
			env.write(" is '");
			switch(strings.get(0)) {
			case PROMPT:
				env.writeln(env.getPromptSymbol().toString() + "'");
				break;
			case MULTILINE:
				env.writeln(env.getMultilineSymbol().toString() + "'");
				break;
			case MORELINES:
				env.writeln(env.getMorelinesSymbol().toString() + "'");
				break;
			default:
				env.writeln("Unsupported symbol name.");
			}
		} else if(strings.size() == 2) {
			if(strings.get(1).length() != 1) {
				env.writeln("The provided symbol must be 1 character long.");
				return ShellStatus.CONTINUE;
			}
			env.write("Symbol for ");
			env.write(strings.get(0));
			env.write(" changed from '");
			switch(strings.get(0)) {
			case PROMPT:
				env.write(env.getPromptSymbol() + "' to '");
				env.setPromptSymbol(strings.get(1).charAt(0));
				env.writeln(env.getPromptSymbol() + "'");
				break;
			case MULTILINE:
				env.write(env.getMultilineSymbol() + "' to '");
				env.setMultilineSymbol(strings.get(1).charAt(0));
				env.writeln(env.getMultilineSymbol() + "'");
				break;
			case MORELINES:
				env.write(env.getMorelinesSymbol() + "' to '");
				env.setMorelinesSymbol(strings.get(1).charAt(0));
				env.writeln(env.getMorelinesSymbol() + "'");
				break;
			default:
				env.writeln("Unsupported symbol name.");
			}
		} else {
			env.writeln("The command " + getCommandName() + " expects one or two arguments.");
		}

		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command is used to modify the symbols that denote");
		list.add("prompt, multiline prompt and line break.");
		list.add("It takes either one or two arguments. If only one argument is");
		list.add("provided, it has to be PROMPT, MULTILINE or MORELINES.");
		list.add("The output will be what the requested symbol is currently set to.");
		list.add("If two arguments are given, the first must be the name of the symbol,");
		list.add("the other one being a character.");
		list.add("The symbol is then changed to the character given.");
		
		return Collections.unmodifiableList(list);
	}
}
