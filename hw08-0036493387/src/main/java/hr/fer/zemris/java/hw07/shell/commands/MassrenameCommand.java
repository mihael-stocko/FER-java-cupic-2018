package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * This command is used for renaming/moving large amounts of files. It takes four or five 
 * arguments, depending on the third one. The first argument is the path to the source folder.
 * The second argument is the path to the destination folder.
 * The third argument is the preferred action. Supported actions are:
 * filter - prints all files from the source folder that match the regular 
 * expression given by the fourth argument.
 * groups - prints all files from the source that match the regular
 * expression given by the fourth argument organized into groups.
 * show - For every file from the source folder that satisfies the regex
 * given by the fourth argument, prints the current name and the name that
 * would be generated if the regular expression given by the fifth argument
 * were to be applied. execute - The command executes the changes shown by the show action.
 * 
 * @author Mihael Stočko
 *
 */
public class MassrenameCommand implements ShellCommand {

	/**
	 * A string literal for the filter action.
	 */
	private static final String filter = "filter";
	
	/**
	 * A string literal for the groups action.
	 */
	private static final String groups = "groups";
	
	/**
	 * A string literal for the show action.
	 */
	private static final String show = "show";
	
	/**
	 * A string literal for the execute action.
	 */
	private static final String execute = "execute";
	
	/**
	 * This pattern is generated from the regular expression given by the fourth argument.
	 */
	private Pattern pattern = null;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		List<String> strings = Util.splitArgs(env, arguments, getCommandName(), 4, true);
		if(strings == null) {
			return ShellStatus.CONTINUE;
		}
		
		try {
			if(strings.get(2).equals(filter)) {
				if(strings.size() != 4) {
					env.writeln("The command " + getCommandName() + " with "
							+ "the CMD " + filter + " expects four arguments.");
					return null;
				}
				
				strings = filter(env, strings);
				if(strings == null) {
					return ShellStatus.CONTINUE;
				}
				
				for(String s : strings) {
					env.writeln(s);
				}
			} else if(strings.get(2).equals(groups)) {
				if(strings.size() != 4) {
					env.writeln("The command " + getCommandName() + " with "
							+ "the CMD + " + groups + " expects four arguments.");
					return null;
				}
				
				List<Matcher> matchers = groups(env, strings);
				for(Matcher matcher : matchers) {
					env.write(matcher.group(0));
					env.write(" ");
					for(int i = 0; i <= matcher.groupCount(); i++) {
						env.write(Integer.valueOf(i).toString());
						env.write(": ");
						env.write(matcher.group(i));
						env.write(" ");
					}
					env.writeln("");
				}
				
			} else if(strings.get(2).equals(show)) {
				if(strings.size() != 5) {
					env.writeln("The command " + getCommandName() + " with "
							+ "the CMD " + show + " expects five arguments.");
					return null;
				}
				
				show(env, groups(env, strings), strings, false);
				
			} else if(strings.get(2).equals(execute)) {
				if(strings.size() != 5) {
					env.writeln("The command " + getCommandName() + " with "
							+ "the CMD " + show + " expects five arguments.");
					return null;
				}
				
				show(env, groups(env, strings), strings, true);
			} else {
				env.writeln("Unsupported CMD.");
			}
		} catch(Exception e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "massrename";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command is used for renaming/moving large amounts of files.");
		list.add("It takes four or five arguments, depending on the third one.");
		list.add("The first argument is the path to the source folder.");
		list.add("The second argument is the path to the destination folder.");
		list.add("The third argument is the preferred action. Supported actions are:");
		list.add("filter - prints all files from the source folder that match the regular");
		list.add("expression given by the fourth argument.");
		list.add("groups - prints all files from the source that match the regular");
		list.add("expression given by the fourth argument organized into groups.");
		list.add("show - For every file from the source folder that satisfies the regex");
		list.add("given by the fourth argument, prints the current name and the name that");
		list.add("would be generated if the regular expression given by the fifth argument");
		list.add("were to be applied.");
		list.add("execute - The command executes the changes shown by the show action.");
		
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * The auxiliary method used for filtering files. Takes all command arguments
	 * and returns a list of file names that satisfy the filter.
	 * 
	 * @param env Environment the command works with
	 * @param strings Command arguments
	 * @return List of filtered file names
	 * @throws ShellIOException
	 */
	private List<String> filter(Environment env, List<String> strings) throws ShellIOException {
		Path p = env.getCurrentDirectory().resolve(Paths.get(strings.get(0)));
		if(!p.toFile().isDirectory()) {
			env.writeln("The given path is not a directory.");
			return null;
		}
		
		String mask = strings.get(3);
		try {
			pattern = Pattern.compile(mask);
		} catch(PatternSyntaxException e) {
			env.writeln("The given regex is not in the correct format.");
			return null;
		}
		
		File[] files = p.toFile().listFiles();
		List<String> filtered = new LinkedList<>();
		for(File f : files) {
			if(f.isDirectory()) {
				continue;
			}
			Matcher matcher = pattern.matcher(f.getName());
			if(matcher.matches()) {
				filtered.add(f.toPath().getFileName().toString());
			}
		}
		
		return filtered;
	}
	
	/**
	 * The auxiliary method used for grouping files. Takes all command arguments
	 * and returns a list of matchers that contain information about every filtered file,
	 * including information about groups.
	 * 
	 * @param env Environment the command works with
	 * @param strings Command arguments
	 * @return List of matchers for all filtered files.
	 * @throws ShellIOException
	 */
	private List<Matcher> groups(Environment env, List<String> strings) throws ShellIOException { 
		List<String> filtered = filter(env, strings);
		List<Matcher> matchers = new LinkedList<>();
		
		for(String f : filtered) {
			Matcher matcher = pattern.matcher(f);
			matcher.find();
			matchers.add(matcher);
		}
		
		return matchers;
	}
	
	/**
	 * Used for show and execute commands. Takes a list of matchers and all command arguments
	 * plus one flag that denotes the wanted behavior. If the flag is <code>false</code>, then
	 * 'show' is executed and the results are printed out. If the flag is <code>true</code>,
	 * the command executes the renaming/moving.
	 * 
	 * @param env Environment the command works with
	 * @param matchers List of matchers for all filtered files.
	 * @param strings Command arguments
	 * @param execute Flag that determines whether 'show' of 'execute' should be carried out.
	 * @throws ShellIOException
	 */
	private void show(Environment env, List<Matcher> matchers, List<String> strings, boolean execute
			) throws ShellIOException {
		NameBuilderParser parser = new NameBuilderParser(strings.get(4));
		NameBuilder builder = parser.getNameBuilder();
		pattern = Pattern.compile(strings.get(3));
		Path source = env.getCurrentDirectory().resolve(Paths.get(strings.get(0)));
		Path destination = env.getCurrentDirectory().resolve(Paths.get(strings.get(1)));

		if(!source.toFile().isDirectory()) {
			env.writeln("The source directory does not exist.");
			return;
		}
		
		if(!destination.toFile().isDirectory()) {
			env.writeln("The destination directory does not exist.");
			return;
		}
		
		for(Matcher matcher : matchers) {
			matcher.find();
			if(matcher.matches()) {
				NameBuilderInfoImpl info = new NameBuilderInfoImpl();
				Map<Integer, String> groups = info.getGroups();
				for(int i = 0; i <= matcher.groupCount(); i++) {
					groups.put(i, matcher.group(i));
				}
				builder.execute(info);
				String newName = info.getStringBuilder().toString();
				if(execute == false) {
					env.write(matcher.group(0));
					env.write(" => ");
					env.writeln(newName);
				} else {
					
					
					try {
						File f = source.resolve(matcher.group(0)).toFile();
						if(f.getName().equals(newName)) {
							continue;
						}
						Files.copy(f.toPath(), destination.resolve(newName), 
								StandardCopyOption.REPLACE_EXISTING);
						Files.delete(f.toPath());
					} catch(IOException e) {
						env.writeln("Cannot move the file.");
						return;
					}
				}
			}
		}
		if(execute) {
			env.writeln("Done.");
		}
	}
}
