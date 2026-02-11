package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellIOException;

/**
 * This class contains auxiliary methods used by shell commands.
 * 
 * @author Mihael Stočko
 *
 */
public class Util {
	
	/**
	 * This method splits the given string by whitespaces and returns it in the form of a
	 * list of strings. Whitespaces will be considered a part of the string if the string is
	 * written under quotation marks. The escape sequences are \" and \\
	 * If the flag {@link regex} is set to <code>true</code>, no escape sequences are allowed.
	 * 
	 * @param s String to be split
	 * @return Resulting list of strings
	 */
	public static List<String> split(String s, boolean regex) {
		List<String> list = new LinkedList<>();
		
		if(s.length() == 0) {
			return list;
		}
		
		char[] chars = s.toCharArray();
		int index = 0;
		StringBuilder sb = new StringBuilder();
		
		boolean quotesMode = false;
		
		while(index < chars.length) {
			if(quotesMode) {
				if(chars[index] == '"') {
					quotesMode = false;
					index++;
					if(index < chars.length && chars[index] != ' ' && chars[index] != '\t') {
						throw new IllegalArgumentException("Closing quote must be folowed by a whitespace.");
					}
					continue;
				}
				if(chars[index] == '\\') {
					if(index == chars.length-2) {
						throw new IllegalArgumentException("Backslash can't be the last char in a string.");
					}
					if(chars[index+1] == '\\' && regex == false) {
						sb.append("\\");
						index += 2;
					} else if(chars[index+1] == '"' && regex == false) {
						sb.append("\"");
						index += 2;
					} else {
						sb.append("\\");
						sb.append(chars[index+1]);
						index += 2;
					}
				} else {
					sb.append(chars[index]);
					index++;
				}
			} else {
				if(chars[index] == ' ' || chars[index] == '\t') {
					index++;
					if(sb.length() > 0) {
						list.add(sb.toString());
						sb.setLength(0);
					}
					continue;
				}
				if(chars[index] == '"') {
					quotesMode = true;
					index++;
					continue;
				}
				sb.append(chars[index]);
				index++;
			}
		}
		
		if(sb.length() > 0 && quotesMode == false) {
			list.add(sb.toString());
		}
		
		if(sb.length() > 0 && quotesMode == true) {
			throw new IllegalArgumentException("The closing quote is missing.");
		}
		
		return list;
	}
	
	/**
	 * Calls the split method with the given string and the flag regex set to false.
	 * 
	 * @param s String to be split
	 * @return Resulting list of strings
	 */
	public static List<String> split(String s) {
		return split(s, false);
	}
	
	/**
	 * Determines if the path p2 is a subdirectory of the path p1.
	 * 
	 * @return <code>true</code> if p2 is a subdirectory of p1, <code>false</code> otherwise.
	 */
	public static boolean isSubdirectory(Path p1, Path p2) {
		while(p2 != null) {
			if(p2.equals(p1)) {
				return true;
			}
			p2 = p2.getParent();
		}
		
		return false;
	}
	
	/**
	 * This method is used for parsing the input arguments into a list of strings.
	 * 
	 * @param env Environment
	 * @param arguments Arguments of the command
	 * @param commandName Name of the command
	 * @param numOfArgs Minimal expected number of arguments
	 * @return Arguments parsed into a list of strings
	 * @throws ShellIOException
	 */
	public static List<String> splitArgs(Environment env, String arguments, String commandName, int numOfArgs,
			boolean additionalArg) 
			throws ShellIOException {
		List<String> strings = null;
		try {
			strings = Util.split(arguments);
		} catch(IllegalArgumentException e) {
			env.writeln("The string under the quotes is not in the required format. " + e.getMessage());
			return null;
		}
		
		if(!additionalArg) {
			if(strings.size() != numOfArgs) {
				env.writeln("The command " + commandName + " expects " + numOfArgs + " argument(s).");
				return null;
			}
		} else {
			if(strings.size() != numOfArgs && strings.size() != numOfArgs+1) {
				env.writeln("The command " + commandName + " expects " + numOfArgs + " or "
						+ (numOfArgs + 1) + " argument(s).");
				return null;
			}
		}
		
		return strings;
	}
}
