package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command takes one path to a directory as an arguemnt.
 * It lists (non recursively) the contents of the given directory.
 * 
 * @author Mihael Stočko
 *
 */
public class LsCommand implements ShellCommand {

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
		if(!p.toFile().isDirectory()) {
			env.writeln("The given path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		File[] files = p.toFile().listFiles();
		for(File f : files) {
			try {
				char[] config = new char[] {'-', '-', '-', '-'};
				if(f.isDirectory()) {
					config[0] = 'd';
				}
				if(f.canRead()) {
					config[1] = 'r';
				}
				if(f.canWrite()) {
					config[2] = 'w';
				}
				if(f.canExecute()) {
					config[3] = 'x';
				}
				env.write(new String(config) + " ");
				
				env.write(String.format("%10d ", f.length()));
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Path path = f.toPath();
				BasicFileAttributeView faView = Files.getFileAttributeView(path, 
						BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				env.write(formattedDateTime + " ");
			} catch(IOException e) {
				env.writeln("Can't get the creation time.");
				return ShellStatus.CONTINUE;
			}
			
			env.writeln(f.getName());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command takes one path to a directory as an arguemnt.");
		list.add("It lists (non recursively) the contents of the given directory.");
		
		return Collections.unmodifiableList(list);
	}
}
