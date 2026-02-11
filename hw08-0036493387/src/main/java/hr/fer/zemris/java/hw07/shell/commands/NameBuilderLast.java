package hr.fer.zemris.java.hw07.shell.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This implementation of the {@link NameBuilder} interface keeps a list of other
 * implementations of the same interface and, when executed, calls the execute method
 * for every one of them.
 * 
 * @author Mihael Stočko
 *
 */
public class NameBuilderLast implements NameBuilder {

	/**
	 * List of other {@link NameBuilder} objects.
	 */
	List<NameBuilder> builders = new LinkedList<>();
	
	/**
	 * Constructor. Takes a list of other NameBuilders and saves it.
	 * 
	 * @param builders List of {@link NameBuilder} objects
	 * @throws NullPointerException
	 */
	public NameBuilderLast(List<NameBuilder> builders) {
		Objects.requireNonNull(builders, "Null received, a list of NameBuilders expected.");
		this.builders = builders;
	}
	
	/**
	 * Calls execute for every NameBuilder in the list.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		for(NameBuilder builder : builders) {
			builder.execute(info);
		}
	}
}
