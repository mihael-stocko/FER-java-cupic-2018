package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Objects;

/**
 * This implementation of the {@link NameBuilder} interface appends a given string literal
 * to the {@link NameBuilderInfo} object.
 * 
 * @author Mihael Stočko
 *
 */
public class NameBuilderLiteral implements NameBuilder {

	/**
	 * The literal to be appended
	 */
	private String literal;
	
	/**
	 * Constructor. Takes a string literal and saves it. The string cannot be null.
	 * 
	 * @param literal The stirng to be appended.
	 * @throws NullPointerException
	 */
	public NameBuilderLiteral(String literal) {
		Objects.requireNonNull(literal, "Canont append null to a NameBuilderInfo.");
		this.literal = literal;
	}
	
	/**
	 * Appends the saved string to the given {@link NameBuilderInfo} object.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(literal);
	}
}
