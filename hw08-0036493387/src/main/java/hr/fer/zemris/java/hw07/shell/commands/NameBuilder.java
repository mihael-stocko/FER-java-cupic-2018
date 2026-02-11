package hr.fer.zemris.java.hw07.shell.commands;

/**
 * Classes that implement this interface must provide a method for appending content
 * to the {@link StringBuilder} gotten from a {@link NameBuilderInfo} object.
 * 
 * @author Mihael Stočko
 *
 */

public interface NameBuilder {

	/**
	 * This method taketh a {@link NameBuilderInfo} object and append some content to it.
	 * 
	 * @param info {@link NameBuilderInfo} object that is being modified.
	 */
	void execute(NameBuilderInfo info);
}
