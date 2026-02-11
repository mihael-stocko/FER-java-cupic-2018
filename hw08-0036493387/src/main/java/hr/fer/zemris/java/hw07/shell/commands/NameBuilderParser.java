package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used for parsing the given expression and creating a {@link NameBuilder} object
 * that will, when executed, create a String object representing a new file name.
 * 
 * @author Mihael Stočko
 *
 */
public class NameBuilderParser {

	/**
	 * List of NameBuilders that will be passed to the last NameBuilder.
	 */
	List<NameBuilder> builders = new LinkedList<>();
	
	/**
	 * The ultimate NameBuilder that executes all of the others.
	 */
	NameBuilderLast ultimateBuilder;
	
	/**
	 * Constructor. Takes a String expression and parses it. Creates a single {@link NameBuilder} object
	 * that can be retrieved by calling getNameBuilder().
	 * 
	 * @param expression The String that is to be parsed.
	 */
	public NameBuilderParser(String expression) {
		char[] exp = expression.toCharArray();
		int index = 0;
		boolean group = false;
		StringBuilder sb = new StringBuilder();
		
		while(index < exp.length) {
			if(group) {
				if(exp[index] != '}') {
					sb.append(exp[index]);
				} else {
					group = false;
					String s = sb.toString();
					sb.setLength(0);
					
					String[] strings = s.split("\\s+");
					
					List<String> stringsList = new ArrayList<>();
					for(int i = 0; i < strings.length; i++) {
						if(!strings[i].equals("")) {
							stringsList.add(strings[i]);
						}
					}
					
					if(stringsList.size() == 3) {
						if(stringsList.get(1).length() != 1 || stringsList.get(1).charAt(0) != ',') {
							throw new IllegalArgumentException("One or more of the groups in the given "
									+ "expression in not in the required format.");
						}
						
						builders.add(new NameBuilderGroup(Integer.parseInt(stringsList.get(0)), 
								stringsList.get(2)));
						
					} else if(stringsList.size() == 2) {
						if(stringsList.get(0).endsWith(",") && !stringsList.get(1).startsWith(",")) {
							builders.add(new NameBuilderGroup(
									Integer.parseInt(stringsList.get(0).substring(0, 
											stringsList.get(0).length()-1)), stringsList.get(1)));
						} else if(!stringsList.get(0).endsWith(",") && stringsList.get(1).startsWith(",")) {
							builders.add(new NameBuilderGroup(
									Integer.parseInt(stringsList.get(0)), 
									stringsList.get(1).substring(1, stringsList.get(1).length())));
						} else {
							throw new IllegalArgumentException("One or more of the groups in the given "
									+ "expression in not in the required format.");
						}
					} else if(stringsList.size() == 1) {
						if(stringsList.get(0).contains(",")) {
							String[] str = stringsList.get(0).split(",");
							if(str.length != 2) {
								throw new IllegalArgumentException("One or more of the groups in the given "
										+ "expression in not in the required format.");
							}
							
							builders.add(new NameBuilderGroup(Integer.parseInt(str[0]), str[1]));
						} else {
							builders.add(new NameBuilderGroup(Integer.parseInt(stringsList.get(0))));
						}
					} else {
						throw new IllegalArgumentException("One or more of the groups in the given "
								+ "expression in not in the required format.");
					}
				}
				index++;
			} else {
				if(index < exp.length-1 && exp[index] == '$' && exp[index+1] == '{') {
					builders.add(new NameBuilderLiteral(sb.toString()));
					sb.setLength(0);
					group = true;
					index += 2;
				} else {
					sb.append(exp[index]);
					index++;
				}
			}
		}
		
		builders.add(new NameBuilderLiteral(sb.toString()));
		
		ultimateBuilder = new NameBuilderLast(builders);
	}
	
	/**
	 * Returns a single {@link NameBuilder} object that has a list of all other NameBuilders created
	 * in the process. A call of execute on this object will result in executing all of the NameBuilders.
	 * 
	 * @return
	 */
	public NameBuilder getNameBuilder() {
		return ultimateBuilder;
	}
}
