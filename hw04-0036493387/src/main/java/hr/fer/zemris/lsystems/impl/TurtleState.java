package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.*;
import java.awt.Color;

/**
 * This class models a state the turtle can be in.
 * 
 * @author Mihael Stočko
 *
 */
public class TurtleState {

	/**
	 * Position of the turtle
	 */
	private Vector2D position;
	
	/**
	 * Angle of the turtle
	 */
	private Vector2D angle;
	
	/**
	 * Color of the pen
	 */
	private Color color;
	
	/**
	 * Length of one single move.
	 */
	private double length;
	
	/**
	 * Constructor
	 * 
	 * @param position Cannot be null
	 * @param angle Cannot be null
	 * @param color Cannot be null
	 * @param length
	 * @throws NullPointerException
	 */
	public TurtleState(Vector2D position, Vector2D angle, Color color, double length) {
		if(position == null) {
			throw new NullPointerException("Position cannot be null.");
		}
		if(angle == null) {
			throw new NullPointerException("Angle cannot be null.");
		}
		if(color == null) {
			throw new NullPointerException("Color cannot be null.");
		}
		
		this.position = position;
		this.angle = angle.scaled(1.0/Math.sqrt(angle.getX()*angle.getX() + angle.getY()*angle.getY()));
		this.color = color;
		this.length = length;
	}	
		
	/**
	 * Getter for position
	 * @return Position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Setter for position
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		if(position == null) {
			throw new NullPointerException("Position cannot be null.");
		}
		
		this.position = position;
	}

	/**
	 * Getter for angle
	 * @return Angle
	 */
	public Vector2D getAngle() {
		return angle;
	}

	/**
	 * Setter for angle
	 * @param angle
	 */
	public void setAngle(Vector2D angle) {
		if(angle == null) {
			throw new NullPointerException("Angle cannot be null.");
		}
		
		this.angle = angle;
	}

	/**
	 * Getter for color
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for color
	 * @param color
	 */
	public void setColor(Color color) {
		if(color == null) {
			throw new NullPointerException("Color cannot be null.");
		}
		
		this.color = color;
	}

	/**
	 * Getter for length
	 * @return Length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Setter for length
	 * @param length
	 */
	public void setLength(double length) {
		this.length = length;
	}
	
	/**
	 * Makes a copy of the current state and returns it.
	 * @return A copy of the turtle's state
	 */
	public TurtleState copy() {
		return new TurtleState(position, angle, color, length);
	}
}
