package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropdCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListdCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassrenameCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopdCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeCommand;
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
	 * A variable holding the shell's current directory.
	 */
	Path currentDirectory;
	
	/**
	 * This map is used for sharing data between methods.
	 */
	Map<String, Object> sharedData;
	
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
		map.put("pwd", new PwdCommand());
		map.put("cd", new CdCommand());
		map.put("pushd", new PushdCommand());
		map.put("popd", new PopdCommand());
		map.put("listd", new ListdCommand());
		map.put("dropd", new DropdCommand());
		map.put("rmtree", new RmtreeCommand());
		map.put("cptree", new CptreeCommand());
		map.put("massrename", new MassrenameCommand());
		
		this.map = Collections.unmodifiableSortedMap(map);
		
		currentDirectory = Paths.get(".");
		sharedData = new HashMap<>();
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
	
	/**
	 * Returns an absolute normalized path to the current directory.
	 * 
	 * @return Current directory
	 */
	@Override
	public Path getCurrentDirectory() {
		return currentDirectory.toAbsolutePath().normalize();
	}
	
	/**
	 * This method sets the current directory to the path given. Cannot be null.
	 * 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		Objects.requireNonNull(path, "Current directory cannot be set to null.");
		if(!path.toFile().exists() || !path.toFile().isDirectory()) {
			throw new IllegalArgumentException("Invalid path.");
		}
		
		currentDirectory = path;
		
	}
	
	/**
	 * Used for getting data shared by methods.
	 * 
	 * @return Shared data for the given key. <code>null</code> if the key doesn't exist.
	 */
	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}
	
	/**
	 * Puts the given object into the map of shared data under the given key.
	 * The key cannot be null.
	 * 
	 * @throws NullPointerException
	 */
	@Override
	public void setSharedData(String key, Object value) {
		Objects.requireNonNull(key, "Key cannot be null");
		sharedData.put(key, value);
	}
}
