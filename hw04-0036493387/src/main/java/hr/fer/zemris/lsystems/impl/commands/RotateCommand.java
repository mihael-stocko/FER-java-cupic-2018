package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.*;
import hr.fer.zemris.math.*;

/**
 * This class is a representation of the command rotate.
 * 
 * @author Mihael Stočko
 *
 */
public class RotateCommand implements Command {

	/**
	 * Angle for which the turtle is rotated.
	 */
	private Vector2D angle;
	
	/**
	 * Constructor
	 * @param angle Cannot be null
	 * @throws NullPointerException
	 */
	public RotateCommand(Vector2D angle) {
		if(angle == null) {
			throw new NullPointerException("Angle cannot be null.");
		}
		
		this.angle = angle;
	}
	
	/**
	 * Takes a TurtleState from the context and rotates the turtle for angle degrees.
	 */
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getAngle().rotate(Math.atan2(angle.getY(), angle.getX())/Math.PI/2*360);
	}
}
