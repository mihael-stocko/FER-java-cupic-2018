package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes 2 arguments - a path to the file that is to
 * be copied, and a path to the location the first file is to be copied
 * to. If a directory is specified as the second argument, it is assumed
 * that the first file is to be copied inside it. This command can also
 * overwrite a file.
 * 
 * @author Mihael Stočko
 *
 */
public class CopyCommand implements ShellCommand {
	
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
		
		if(strings.size() != 2) {
			env.writeln("The command " + getCommandName() + " expects two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Path p1 = Paths.get(strings.get(0));
		Path p2 = Paths.get(strings.get(1));
		
		if(p1.toFile().isDirectory()) {
			env.writeln("Copying directories is not supported.");
			return ShellStatus.CONTINUE; 
		}
		
		if(!p2.toFile().getParentFile().exists()) {
			env.writeln("The given folder structure for the destination does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(p2.toFile().isFile() && p2.toFile().exists()) {
			env.write("The destination file already exists. Do you want to overwrite it? (y/n) ");
			char answer = askYesNo(env);
			if(answer == 'n') {
				env.writeln("K.");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(p2.toFile().isDirectory()) {
			p2 = p2.resolve(p1.getFileName());
		}
		
		try (InputStream is = new BufferedInputStream(new FileInputStream(p1.toFile()));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(p2.toFile()))) {
			p2.toFile().createNewFile();
			while(true) {
				byte[] bytes = new byte[1024];
				int r = is.read(bytes);
				if(r < 1) {
					break;	
				}
				os.write(bytes);
			}
			
		} catch(IOException e) {
			env.writeln("Can't copy the file.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Done.");
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Asks the user to enter either 'y' or 'n' until the requested character has been got.
	 * 
	 * @param env Environment object
	 * @return The user's answer
	 * @throws ShellIOException
	 */
	private char askYesNo(Environment env) throws ShellIOException {
		Scanner sc = new Scanner(System.in);
		String ans = null;
		while(sc.hasNext()) {
			ans = sc.next();
			if(!(ans.equals("y") || ans.equals("n"))) {
				env.write("Dude. 'y' or 'n'? ");
			} else {
				break;
			}
		}
		return ans.charAt(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes 2 arguments - a path to the file that is to");
		list.add("be copied, and a path to the location the first file is to be copied");
		list.add("to. If a directory is specified as the second argument, it is assumed");
		list.add("that the first file is to be copied inside it. This command can also");
		list.add("overwrite a file.");
		
		return Collections.unmodifiableList(list);
	}
}
