package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a 3-dimensional vector. It offers methods for various calculations
 * on vectors like addition, subtraction, scaling, dot product and cross product.
 * 
 * @author Mihael Stočko
 *
 */
public class Vector3 {

	/**
	 * X component
	 */
	private final double x;
	
	/**
	 * Y component
	 */
	private final double y;
	
	/**
	 * Z component
	 */
	private final double z;
	
	/**
	 * Constructor.
	 * 
	 * @param x X component
	 * @param y Y component
	 * @param z Z component
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates the norm of the vector.
	 * 
	 * @return norm of the vector
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Returns a new vector that is obtained by normalizing this vector.
	 * 
	 * @return Normalized vector
	 */
	public Vector3 normalized() {
		return new Vector3(x/norm(), y/norm(), z/norm());
	}
	
	/**
	 * Add this vector to the given vector.
	 * 
	 * @param other Second operand
	 * @return A new vector that is the sum of these two vectors.
	 * @throws NullPointerException
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other, "Cannot add null to a vector.");
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * 
	 * @param other Second operand
	 * @return A new vector that is the difference of these two vectors.
	 * @throws NullPointerException
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other, "Cannot subtract null from a vector.");
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}
	
	/**
	 * Calculates the dot product of this vector and the given vector.
	 * 
	 * @param other Second operand
	 * @return Dot product
	 * @throws NullPointerException
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other, "Null is not an acceptable argument for a dot product.");
		return x*other.x + y*other.y + z*other.z;
	}
	
	/**
	 * Calculates the cross product of this vector and the given vector.
	 * 
	 * @param other Second operand
	 * @return A new vector that is obtained by calculating the cross product.
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other, "Null is not an acceptable argument for a cross product.");
		return new Vector3(y*other.z - z*other.y, -(x*other.z - z*other.x), x*other.y-y*other.x);
	}
	
	/**
	 * Scales this vector by the given fator.
	 * 
	 * @param s Factor for scaling
	 * @return Scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x*s, y*s, z*s);
	}
	
	/**
	 * Calculates the cosine of the angle between this vector and the one given.
	 * 
	 * @param other Second vector.
	 * @return Cosine of the angle
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other, "Null is not an acceptable argument for calculating an angle.");
		return this.dot(other)/this.norm()/other.norm();
	}
	
	/**
	 * Getter for X component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for Y component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Getter for Z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Converts this vector to an array of double values.
	 * 
	 * @return Array of doubles
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	/**
	 * Returns a string interpretation of this vector.
	 */
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
