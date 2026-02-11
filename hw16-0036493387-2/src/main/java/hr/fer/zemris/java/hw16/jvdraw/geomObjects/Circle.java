package hr.fer.zemris.java.hw16.jvdraw.geomObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * This class models a circle.
 * 
 * @author Mihael Stočko
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Center x coordinate
	 */
	int c1;
	
	/**
	 * Center y coordinate
	 */
	int c2;
	
	/**
	 * Radius
	 */
	double r;
	
	/**
	 * Color of the circle
	 */
	Color color;

	/**
	 * Constructor.
	 */
	public Circle(int c1, int c2, double r) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.r = r;
	}

	/**
	 * Getter for c1
	 */
	public int getC1() {
		return c1;
	}

	/**
	 * Setter for c1
	 */
	public void setC1(int c1) {
		this.c1 = c1;
		notifyListenersChanged();
	}

	/**
	 * Getter for c2
	 */
	public int getC2() {
		return c2;
	}

	/**
	 * Setter for c2
	 */
	public void setC2(int c2) {
		this.c2 = c2;
		notifyListenersChanged();
	}

	/**
	 * Getter for radius
	 */
	public double getR() {
		return r;
	}

	/**
	 * Setter for radius
	 */
	public void setR(double r) {
		this.r = r;
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
		return new CircleEditor(this);
	}
}
