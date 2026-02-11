package hr.fer.zemris.java.hw07.shell.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an implementation of the NameBuilderInfo interface. It has a string builder that
 * can be retrieved and built upon, and also keeps a map of groups.
 * 
 * @author Mihael Stočko
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

	/**
	 * The string builder that is being built.
	 */
	StringBuilder sb = new StringBuilder();
	
	/**
	 * A map that maps a number to the content of the respective group.
	 */
	Map<Integer, String> groups = new HashMap<>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder getStringBuilder() {
		return sb;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGroup(int index) {
		return groups.get(index);
	}
	
	/**
	 * Returns the internally kept map of groups.
	 */
	public Map<Integer, String> getGroups() {
		return groups;
	}
}
