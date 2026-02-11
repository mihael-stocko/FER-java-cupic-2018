package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class is a representation of the command color.
 * 
 * @author Mihael Stočko
 *
 */
public class ColorCommand implements Command {
	
	/**
	 * Color of the pen
	 */
	private Color color;

	/**
	 * Constructor
	 * @param color
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}
	
	/**
	 * Takes a TurtleState from the context and changes its color to the color provided through the
	 * constructor.
	 */
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}
}
