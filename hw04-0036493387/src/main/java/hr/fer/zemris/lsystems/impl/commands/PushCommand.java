package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.*;

/**
 * This class is a representation of the command push.
 * 
 * @author Mihael Stočko
 *
 */
public class PushCommand implements Command {
	
	/**
	 * Makes a copy of the state from the top of the context and pushes it onto the context.
	 */
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}
}
