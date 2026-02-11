package hr.fer.zemris.math;

/**
 * A simple representation of a two-dimensional vector.
 * 
 * @author Mihael Stočko
 *
 */
public class Vector2D {
	
	/**
	 * x coordinate
	 */
	private double x;
	
	/**
	 * y coordinate
	 */
	private double y;
	
	/**
	 * Constructor
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates itself using the vector provided.
	 * 
	 * @param offset Vector used for translation
	 */
	public void translate(Vector2D offset) {
		x = translated(offset).getX();
		y = translated(offset).getY();
	}
	
	/**
	 * Returns a new vector that represents the result of translating this vector with the vector provided.
	 * 
	 * @param offset Vector used for translation
	 * @return The result of translation
	 * @throws NullPointerException
	 */
	public Vector2D translated(Vector2D offset) {
		if(offset == null) {
			throw new NullPointerException("The argument for the method translate cannot be null.");
		}
		
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}
	
	/**
	 * Rotates itself using the angle provided.
	 * 
	 * @param angle Angle for the rotation
	 */
	public void rotate(double angle) {
		Vector2D newVector = rotated(angle);
		x = newVector.getX();
		y = newVector.getY();
	}
	
	/**
	 * Returns a new vector that represents the result of rotating this vector for the provided angle.
	 * 
	 * @param angle Angle for the rotation
	 * @return The result of rotation
	 */
	public Vector2D rotated(double angle) {
		double currentAngle = Math.atan2(y, x);
		double magnitude = Math.sqrt(x*x + y*y);
		currentAngle += angle/360*Math.PI*2;
		double newX = Math.cos(currentAngle)*magnitude;
		double newY= Math.sin(currentAngle)*magnitude;
		return new Vector2D(newX, newY);
	}
	
	/**
	 * Scales itself using the scaler provided.
	 * 
	 * @param scaler Scaler used for the scaling
	 */
	public void scale(double scaler) {
		Vector2D newVector = scaled(scaler);
		x = newVector.getX();
		y = newVector.getY();
	}
	
	/**
	 * Returns a new vector that represents the result of scaling this vector with the provided scaler.
	 * 
	 * @param scaler Scaler for the scaling
	 * @return The result of scaling
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * Math.cos(Math.atan2(y, x)) * scaler, y * Math.sin(Math.atan2(y, x)) * scaler);
	}
	
	/**
	 * Returns a copy of itself
	 * 
	 * @return A copy of itself
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
