package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.*;

/**
 * This interface defines a single method - execute. It must be implemented by the commands used
 * by LSystem.
 * 
 * @author Mihael Stočko
 *
 */
public interface Command {
	
	/**
	 * Takes a context and a painter as arguments and executes an appropriate action on the context.
	 * 
	 * @param ctx Context
	 * @param painter An instance of the Painter class.
	 */
	void execute(Context ctx, Painter painter);
}
