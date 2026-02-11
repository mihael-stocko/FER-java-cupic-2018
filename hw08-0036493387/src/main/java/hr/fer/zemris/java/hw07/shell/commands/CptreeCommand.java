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
 * Takes two paths as arguments. Copies the tree given by the first one
 * to the location given by the second argument. If the directory given by
 * the second argument exists, the tree is copied into it. If it doesn't,
 * but its parent does, then it is assumed that the tree given by the first
 * argument is wanted to be copied under the name given by the second argument.
 * 
 * @author Mihael Stočko
 *
 */
public class CptreeCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		List<String> strings = Util.splitArgs(env, arguments, getCommandName(), 2, false);
		if(strings == null) {
			return ShellStatus.CONTINUE;
		}
		
		Path source = env.getCurrentDirectory().resolve(Paths.get(strings.get(0)));
		Path destination = env.getCurrentDirectory().resolve(Paths.get(strings.get(1)));
		
		if(destination.toFile().exists()) {
			destination = destination.resolve(source.getParent().relativize(source));
		} else if(!destination.getParent().toFile().exists()) {
			env.writeln("The given destination is invalid.");
		}
		
		if(Util.isSubdirectory(source, destination)) {
			env.writeln("The given destination is either the same or a subdirectory of the given source.");
			return ShellStatus.CONTINUE;
		}
		
		Path p = destination;
		
		try {
			Files.createDirectories(destination);
		} catch(IOException e) {
			env.writeln("Cannot create a destination directory.");
		}
		
		try {
			Files.walkFileTree(source, new FileVisitor<Path>() {
				@Override
				public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
					Files.createDirectories(p.resolve(source.relativize(arg0)));
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
					Files.copy(arg0, p.resolve(source.relativize(arg0)));
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
					//throw new IOException();
					return FileVisitResult.CONTINUE;
				}
				
			});
		} catch(IOException e) {
			env.writeln("Cannot copy the file tree.");
		}
		
		env.writeln("Done.");
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cptree";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Takes two paths as arguments. Copies the tree given by the first one");
		list.add("to the location given by the second argument. If the directory given by");
		list.add("the second argument exists, the tree is copied into it. If it doesn't,");
		list.add("but its parent does, then it is assumed that the tree given by the first");
		list.add("argument is wanted to be copied under the name given by the second argument.");
		
		return Collections.unmodifiableList(list);
	}
}
