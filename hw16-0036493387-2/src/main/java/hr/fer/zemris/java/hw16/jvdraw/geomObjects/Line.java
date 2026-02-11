package hr.fer.zemris.java.hw16.jvdraw.geomObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * This class models a line.
 * 
 * @author Mihael Stočko
 *
 */
public class Line extends GeometricalObject {
	
	/**
	 * Starting x coordinate.
	 */
	int x1;
	
	/**
	 * Starting y coordinate.
	 */
	int x2;
	
	/**
	 * Ending x coordinate.
	 */
	int y1;
	
	/**
	 * Ending y coordinate.
	 */
	int y2;
	
	/**
	 * Color of the line
	 */
	Color color;
	
	/**
	 * Constructor.
	 */
	public Line(int x1, int y1 , int x2, int y2) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	/**
	 * Setter for points.
	 * 
	 * @param x1 Starting x coordinate.
	 * @param y1 Starting y coordinate.
	 * @param x2 Ending x coordinate.
	 * @param y2 Ending y coordinate.
	 */
	public void setPoints(int x1, int y1 , int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		notifyListenersChanged();
	}

	/**
	 * Getter for color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for color
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyListenersChanged();
	}

	/**
	 * Getter for starting x coordinate
	 */
	public int getX1() {
		return x1;
	}
	
	/**
	 * Setter for starting x coordinate
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		notifyListenersChanged();
	}

	/**
	 * Getter for ending x coordinate
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Setter for ending x coordinate
	 */
	public void setX2(int x2) {
		this.x2 = x2;
		notifyListenersChanged();
	}

	/**
	 * Getter for starting y coordinate
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Setter for starting y coordinate
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		notifyListenersChanged();
	}

	/**
	 * Getter for ending y coordinate
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Setter for ending y coordinate
	 */
	public void setY2(int y2) {
		this.y2 = y2;
		notifyListenersChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
}
