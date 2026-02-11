package hr.fer.zemris.java.hw07.shell.commands;

/**
 * This is an implementation of {@link NameBuilder}. It appends the content of a group
 * indexed through the constructor to the {@link NameBuilderInfo} object.
 * 
 * @author Mihael Stočko
 *
 */
public class NameBuilderGroup implements NameBuilder {
	
	/**
	 * Indexed group
	 */
	private int group;
	
	/**
	 * If this flag is set to <code>true</code>, the difference between the minimal and actual
	 * length of the content of the group will be filled with zeroes. Otherwise, it will be filled
	 * with whitespaces.
	 */
	private boolean zeroes;
	
	/**
	 * Minimal length of the group content
	 */
	private int format;
	
	/**
	 * Constructor. Takes the index of a group to be appended to the {@link NameBuilderInfo}.
	 * 
	 * @param group Group to be appended
	 */
	public NameBuilderGroup(int group) {
		this(group, Integer.valueOf(0).toString());
	}
	
	/**
	 * Constructor. Takes the index of a group to be appended to the {@link NameBuilderInfo} and
	 * some additional information - minimal length of the string to be appended and whether the
	 * residual space should be filled with zeroes or whitespaces.
	 * 
	 * @param group Group to be appended
	 * @param additionalInfo Additional information.
	 */
	public NameBuilderGroup(int group, String additionalInfo) {
		if(group < 0) {
			throw new IllegalArgumentException("Group number cannot be less than 0, was " + group + ".");
		}	
		
		int length = additionalInfo.length();
		if(length != 1 && length != 2 || length == 2 && additionalInfo.charAt(0) != '0') {
			throw new IllegalArgumentException("Additional info is in a wrong format.");
		}
		
		try {
			format = Integer.parseInt(additionalInfo);
		} catch(Exception e) {
			throw new IllegalArgumentException("Cannot parse the given additional info.");
		}
		
		if(length == 1) {
			zeroes = false;
		} else {
			zeroes = true;
		}
		
		this.group = group;
	}
	
	/**
	 * Appends the content of the group indexed through the constructor to the given {@link NameBuilderInfo}
	 * object taking into account the additional information if provided.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		String g = info.getGroup(group);
		StringBuilder sb = info.getStringBuilder();
		if(g.length() < format) {
			int diff = format - g.length();
			for(int i = 0; i < diff; i++) {
				sb.append(zeroes ? '0' : ' ');
			}
		}
		sb.append(g);
	}
}
