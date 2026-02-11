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
 * Takes a path to a directory and deletes it and all of its content.
 * An error message is written if the given directory does not exist.
 * 
 * @author Mihael Stočko
 *
 */
public class RmtreeCommand implements ShellCommand {

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
		
		Path p = env.getCurrentDirectory().resolve(Paths.get(strings.get(0)));
		if(!p.toFile().isDirectory()) {
			env.writeln("The given path is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(p, new FileVisitor<Path>() {
				@Override
				public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
					Files.delete(arg0);
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
					Files.delete(arg0);
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
					//throw new IOException();
					return FileVisitResult.CONTINUE;
				}
				
			});
		} catch(IOException e) {
			env.writeln("Cannot delete the file tree.");
		}
		
		env.writeln("Done.");
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "rmtree";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Takes a path to a directory and deletes it and all of its content.");
		
		return Collections.unmodifiableList(list);
	}
}

