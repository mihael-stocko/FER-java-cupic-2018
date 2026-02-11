package hr.fer.zemris.java.hw16.jvdraw.geomObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * This class models a filled circle.
 * 
 * @author Mihael Stočko
 *
 */
public class FilledCircle extends GeometricalObject {

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
	Color color1;
	
	/**
	 * Color of the background
	 */
	Color color2;
	
	/**
	 * Constructor.
	 */
	public FilledCircle(int c1, int c2, double r) {
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
	 * Getter for color1
	 */
	public Color getColor1() {
		return color1;
	}

	/**
	 * Setter for color1
	 */
	public void setColor1(Color color1) {
		this.color1 = color1;
		notifyListenersChanged();
	}

	/**
	 * Getter for color2
	 */
	public Color getColor2() {
		return color2;
	}

	/**
	 * Setter for color2
	 */
	public void setColor2(Color color2) {
		this.color2 = color2;
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
		return new FilledCircleEditor(this);
	}
}
