package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.*;

/**
 * This class is a representation of the command pop.
 * 
 * @author Mihael Stočko
 *
 */
public class PopCommand implements Command {
	
	/**
	 * Pops a state from the context.
	 */
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
