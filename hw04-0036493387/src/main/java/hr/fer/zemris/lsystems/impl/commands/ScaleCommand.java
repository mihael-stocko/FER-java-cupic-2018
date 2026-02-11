package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class is a representation of the command scale.
 * 
 * @author Mihael Stočko
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Factor to multiply the unitLength with.
	 */
	private double factor;

	/**
	 * Constructor
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}
	
	/**
	 * Takes a TurtleState from the context and multiplies its single move length.
	 */
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setLength(ctx.getCurrentState().getLength()*factor);
	}
}
