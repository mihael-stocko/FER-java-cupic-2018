package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a complex number. It offers methods for various calculations
 * on complex numbers like addition, subtraction, multiplication and division.
 * 
 * @author Mihael Stočko
 *
 */
public class Complex {
	
	/**
	 * An object representing the complex number 0 + 0i.
	 */
	public static final Complex ZERO = new Complex(0, 0);
	
	/**
	 * An object representing the complex number 1 + 0i.
	 */
	public static final Complex ONE = new Complex(1, 0);
	
	/**
	 * An object representing the complex number -1 + 0i.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	
	/**
	 * An object representing the complex number 0 + 1i.
	 */
	public static final Complex IM = new Complex(0, 1);
	
	/**
	 * An object representing the complex number 0 - 1i.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Real component
	 */
	private final double re;
	
	/**
	 * Imaginary component
	 */
	private final double im;
	
	/**
	 * Default constructor. Creates a complex number 0 + 0i.
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param re Real component
	 * @param im Imaginary component
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Getter for the real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter for the imaginary part
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Calculates the module of this complex number.
	 * 
	 * @return Module of the number.
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * Multiplies itself with the complex number from the argument.
	 * 
	 * @param c Operand for the multiplication.
	 * @return A new complex number, product of the two used.
	 * @throws NullPointerException
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c, "Null is not an acceptable argument for multiplication.");
		return new Complex(re*c.re - im*c.im, re*c.im + im*c.re);
	}
	
	/**
	 * Divides itself with the complex number from the argument.
	 * 
	 * @param c Operand for the division.
	 * @return A new complex number, quotient of the two used.
	 * @throws NullPointerException
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c, "Null is not an acceptable argument for division.");
		
		Complex conjugate = new Complex(c.re, -c.im);
		Complex dividend = this.multiply(conjugate);
		Complex divisor = c.multiply(conjugate);
		
		return new Complex(dividend.re/divisor.re, dividend.im/divisor.re);
	}
	
	/**
	 * Adds itself to the complex number from the argument.
	 * 
	 * @param c Operand for the addition.
	 * @return A new complex number, sum of the two used.
	 * @throws NullPointerException
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c, "Cannot add null to a complex number.");
		return new Complex(re+c.re, im+c.im);
	}
	
	/**
	 * Subtracts the argument from itself.
	 * 
	 * @param c Operand for the subtraction.
	 * @return A new complex number, difference of the two used.
	 * @throws NullPointerException
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c, "Cannot subtract null from a complex number.");
		return new Complex(re-c.re, im-c.im);
	}
	
	/**
	 * Negates this complex number.
	 * 
	 * @return Negated number.
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Raises itself to the power of the argument.
	 * 
	 * @param n Exponent
	 * @return A new complex number, result of the exponentiation.
	 * @throws IllegalArgumentException
	 */
	public Complex power(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Exponent cannot be smaller than zero, was " + n + ".");
		}
		
		if(n == 0) {
			return ONE;
		} else {
			Complex temp = ONE;
			for(int i = 0; i < n; ++i) {
				temp = temp.multiply(this);
			}
			return temp;
		}
	}

	/**
	 * Returns the angle of the complex number.
	 * 
	 * @return Angle of the complex number.
	 */
	private double getAngle() {
		return Math.atan2(im, re);
	}
	
	/**
	 * Takes the n-th root from the complex number.
	 * 
	 * @param n Exponent
	 * @return A list of complex numbers, roots of the original number.
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Cannot calculate roots smaller than 1, n was " + n + ".");
		}
		
		List<Complex> roots = new ArrayList<>();
		
		for(int i = 0; i < n; ++i) {
			double factor = Math.pow(this.module(), 1.0/n);
			Complex root = new Complex(factor*Math.cos((this.getAngle()+i*2*Math.PI)/n), factor*Math.sin((this.getAngle()+i*2*Math.PI)/n));
			roots.add(root);
		}
		
		return roots;
	}
	
	/**
	 * Returns a string interpretation of this complex number.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(re != 0) {
			sb.append(re);
		}
		
		if(im != 0) {
			if(re != 0) {
				if(im < 0) {
					sb.append(im);
				} else {
					sb.append('+');
					sb.append(im);
				}
			} else {
				sb.append(im);
			}
			sb.append('i');
		}
		
		return sb.toString();
	}
}
