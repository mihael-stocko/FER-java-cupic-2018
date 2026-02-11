package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes one path to a directory as an arguemnt.
 * It lists (recursively) the contents of the given directory in the tree form.
 * 
 * @author Mihael Stočko
 *
 */
public class TreeCommand implements ShellCommand {

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
		
		Path p = Paths.get(strings.get(0)).normalize();
		if(!p.toFile().isDirectory()) {
			env.writeln("The given path is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(p, new FileVisitor<Path>() {
				private int indentation = 0;
				
				@Override
				public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
					indentation -= 2;
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
					indentation += 2;
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
					try {
						for(int i = 0; i < indentation; i++) {
							env.write(" ");
						}
						env.writeln(arg0.getFileName().toString());
					} catch(ShellIOException e) {
						throw new IOException();
					}
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
					//throw new IOException();
					return FileVisitResult.CONTINUE;
				}
				
			});
		} catch(IOException e) {
			env.writeln("Cannot walk the file tree.");
			return ShellStatus.CONTINUE;
		} 
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes one path to a directory as an arguemnt.");
		list.add("It lists (recursively) the contents of the given directory in the tree form.");
		
		return Collections.unmodifiableList(list);
	}
}
