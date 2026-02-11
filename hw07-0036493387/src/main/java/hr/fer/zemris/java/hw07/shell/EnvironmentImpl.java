package hr.fer.zemris.java.hw07.shell;

import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeCommand;

/**
 * An implementation of the Environment interface.
 * 
 * @author Mihael Stočko
 *
 */
public class EnvironmentImpl implements Environment {

	/**
	 * A Scanner used for reading from the standard input.
	 */
	private Scanner sc;
	
	/**
	 * The character currently being used as a prompt symbol.
	 */
	private Character PROMPTSYMBOL;
	
	/**
	 * The character currently being used as a multiline symbol.
	 */
	private Character MULTILINESYMBOL;
	
	/**
	 * The character currently being used as a symbol for breaking the input into several lines.
	 */
	private Character MORELINESSYMBOL;
	
	/**
	 * A map holding the registered commands.
	 */
	SortedMap<String, ShellCommand> map;
	
	/**
	 * Constructor. Fills the map with the supported commands and sets the symbols
	 * to initial values.
	 */
	public EnvironmentImpl() {
		sc = new Scanner(System.in);
		PROMPTSYMBOL = '>';
		MULTILINESYMBOL = '|';
		MORELINESSYMBOL = '\\';
		
		SortedMap<String, ShellCommand> map = new TreeMap<>();
		map.put("symbol", new SymbolCommand());
		map.put("exit", new ExitCommand());
		map.put("charsets", new CharsetsCommand());
		map.put("cat", new CatCommand());
		map.put("ls", new LsCommand());
		map.put("tree", new TreeCommand());
		map.put("copy", new CopyCommand());
		map.put("mkdir", new MkdirCommand());
		map.put("hexdump", new HexdumpCommand());
		map.put("help", new HelpCommand());
		
		this.map = Collections.unmodifiableSortedMap(map);
	}
	
	/**
	 * Reads a line from the standard input and returns it.
	 * 
	 * @return The string that has been read.
	 * @throws ShellIOException
	 */
	@Override
	public String readLine() throws ShellIOException {
		System.out.print(PROMPTSYMBOL + " ");
		StringBuilder sb = new StringBuilder();
		while(sc.hasNext()) {
			String temp = sc.nextLine();
			if(temp.charAt(temp.length()-1) == MORELINESSYMBOL) {
				sb.append(temp.substring(0, temp.length()-1));
				System.out.print(MULTILINESYMBOL + " ");
			} else {
				sb.append(temp);
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * Writes the provided string to the standard output.
	 * @param text
	 * @throws ShellIOException
	 */
	@Override
	public void write(String text) throws ShellIOException {
		Objects.requireNonNull(text, "write expects some argument.");
		System.out.print(text);
		
	}
	
	/**
	 * Writes the provided string to the standard output and starts a new line.
	 * 
	 * @param text
	 * @throws ShellIOException
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		Objects.requireNonNull(text, "writeln expects some argument.");
		System.out.println(text);
		
	}
	
	/**
	 * Returns a map of all registered commands.
	 * @return A map of commands.
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return map;
	}
	
	/**
	 * Getter for MULTILINE symbol
	 * @param symbol
	 */
	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}
	
	/**
	 * Setter for MULTILINE symbol
	 * @param symbol
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		Objects.requireNonNull(symbol, "MORELINESSYMBOL cannot be set to null.");
		MORELINESSYMBOL = symbol;
	}
	
	/**
	 * Getter for PROMPT symbol
	 * @param symbol
	 */
	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}
	
	/**
	 * Setter for PROMPT symbol
	 * @param symbol
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		Objects.requireNonNull(symbol, "MULTILINESYMBOL cannot be set to null.");
		MULTILINESYMBOL = symbol;
	}
	
	/**
	 * Getter for MORELINES symbol
	 * @param symbol
	 */
	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}
	
	/**
	 * Setter for MORELINES symbol
	 * @param symbol
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		Objects.requireNonNull(symbol, "PROMPTSYMBOL cannot be set to null.");
		PROMPTSYMBOL = symbol;
	}
}
