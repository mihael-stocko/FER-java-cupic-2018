package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * This command takes the path to a file as an argument and
 * prints the whole file out in the hexadecimal format.
 * 
 * @author Mihael Stočko
 *
 */
public class HexdumpCommand implements ShellCommand {

	/**
	 * Number of bytes to be read or written in one loop.
	 */
	private static int numberOfBytes = 16;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		List<String> strings = Util.splitArgs(env, arguments, getCommandName(), 1, false);
		if(strings == null) {
			return ShellStatus.CONTINUE;
		}
		
		Path p = env.getCurrentDirectory().resolve(Paths.get(strings.get(0)));
		if(p.toFile().isDirectory()) {
			env.writeln("A directory is not an acceptable argument for " + getCommandName() + ".");
			return ShellStatus.CONTINUE;
		}
		
		try (InputStream is = new BufferedInputStream(new FileInputStream(p.toFile()))) {
			int number = 0;
			while(true) {
				byte[] bytes = new byte[numberOfBytes];
				int r = is.read(bytes);
				if(r < 1) {
					break;	
				}
				env.write(String.format("%08X: ", number));
				number += numberOfBytes;
				String hex = hr.fer.zemris.java.hw07.crypto.Util.bytetohex(bytes).toUpperCase();
				for(int i = 0; i < numberOfBytes*2; i += 2) {
					if(i < r*2) {
						env.write(hex.substring(i, i+2));
					} else {
						env.write("  ");
					}
					if(i == numberOfBytes-2) {
						env.write("|");
					} else {
						env.write(" ");	
					}
				}
				env.write("| ");
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < r; i++) {
					if(bytes[i] < 32 || bytes[i] > 127) {
						sb.append('.');
					} else {
						sb.append((char)bytes[i]);
					}
				}
				env.writeln(sb.toString());
			}
		} catch(IOException e) {
			env.writeln("Can't produce the hexdump.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes the path to a file as an argument and");
		list.add("prints the whole file out in the hexadecimal format.");
		
		return Collections.unmodifiableList(list);
	}
}
