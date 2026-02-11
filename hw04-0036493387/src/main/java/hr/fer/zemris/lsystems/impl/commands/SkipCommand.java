package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * This class is a representation of the command skip.
 * 
 * @author Mihael Stočko
 *
 */
public class SkipCommand implements Command {

	/**
	 * Number of unitLenght-s to skip
	 */
	private double step;
	
	/**
	 * Constructor
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Takes a TurtleState from the context and moves the turtle forward for unitLenght*step units
	 * without drawing the line.
	 */
	public void execute(Context ctx, Painter painter) {
		Vector2D position = ctx.getCurrentState().getPosition();
		double angle = Math.atan2(ctx.getCurrentState().getAngle().getY(), 
				ctx.getCurrentState().getAngle().getX());
		double length = ctx.getCurrentState().getLength();
		
		Vector2D vector = new Vector2D(step*length*Math.cos(angle), step*length*Math.sin(angle));
		
		Vector2D newVector = position.translated(vector);
		
		ctx.getCurrentState().setPosition(newVector);
	}
}
