package hr.fer.zemris.java.hw16.jvdraw.visitors;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FPoly;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Line;

/**
 * This interface models a geometrical object visitor. It has different visit methods for
 * different geometrical objects.
 * 
 * @author Mihael Stočko
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Called when visiting a line.
	 */
	public abstract void visit(Line line);
	
	/**
	 * Called when visiting a circle.
	 */
	public abstract void visit(Circle circle);
	
	/**
	 * Called when visiting a filled circle.
	 */
	public abstract void visit(FilledCircle filledCircle);
	
	public abstract void visit(FPoly fpoly);
}
