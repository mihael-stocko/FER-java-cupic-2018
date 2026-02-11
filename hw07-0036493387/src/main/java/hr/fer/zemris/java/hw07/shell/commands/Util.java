package hr.fer.zemris.java.hw07.shell.commands;

import java.util.LinkedList;
import java.util.List;

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
	 * written under quotation marks.
	 * 
	 * @param s String to be split
	 * @return List of strings
	 */
	public static List<String> split(String s) {
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
					if(chars[index+1] == '\\') {
						sb.append("\\");
						index += 2;
					} else if(chars[index+1] == '"') {
						sb.append("\"");
						index += 2;
					} else {
						sb.append(chars[index+1]);
						index++;
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
}
