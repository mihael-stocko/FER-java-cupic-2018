package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a complex polynomial. It offers methods for some operations
 * on the given polynomial.
 * 
 * @author Mihael Stočko
 *
 */
public class ComplexPolynomial {

	/**
	 * Factors of the polynomial
	 */
	Complex[] factors;
	
	/**
	 * Constructor. It takes an arbitrary positive number of factors for the polynomial.
	 * 
	 * @param factors
	 * @throws IllegalArgumentException
	 */
	public ComplexPolynomial(Complex... factors) {
		if(factors.length < 1) {
			throw new IllegalArgumentException("At least one factor must be provided.");
		}
		this.factors = factors;
	}
	
	/**
	 * Returns the order of the polynomial.
	 * @return order of the polynomial
	 */
	public short order() {
		return (short)(factors.length-1);
	}
	
	/**
	 * Multiplies this polynomial with the polynomial given.
	 * 
	 * @param p The second polynomial.
	 * @return Another polynomial got by multiplying this polynomial with the given polynomial.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p, "Cannot multiply the polynomial with null.");
		
		Complex[] product = new Complex[this.order() + p.order() + 1];
		for(int i = 0; i < product.length; i++) {
			product[i] = Complex.ZERO;
		}
		
		for(int i = 0; i < p.factors.length; i++) {
			for(int j = 0; j < this.factors.length; j++) {
				product[i+j] = product[i+j].add(p.factors[i].multiply(this.factors[j]));
			}
		}
		
		return new ComplexPolynomial(product);
	}
	
	/**
	 * Derives this polynomial.
	 * @return A new polynomial - first derivative of this polynomial.
	 */
	public ComplexPolynomial derive() {
		if(factors.length == 1) {
			throw new UnsupportedOperationException("A derivative of a polynomial of order zero "
					+ "cannot be represented by this type.");
		}
		
		Complex[] derivative = new Complex[factors.length-1];
		
		for(int i = 0; i < derivative.length; i++) {
			derivative[i] = factors[i].multiply(new Complex(factors.length-1-i, 0));
		}
		
		return new ComplexPolynomial(derivative);
	}
	
	/**
	 * Evaluates this polynomial at the given parameter z.
	 * 
	 * @param z parameter for evaluation
	 * @return Value of the polynomial for the given parameter.
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "The polynomial cannot be evaluated with null.");
		
		Complex result = Complex.ZERO;
		for(int i = 0; i < factors.length-1; i++) {
			result = result.add(factors[i].multiply(z.power(factors.length-1-i)));
		}
		
		result = result.add(factors[factors.length-1]);
		
		return result;
	}
	
	/**
	 * A string representation of this polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < factors.length-1; i++) {
			sb.append('(');
			sb.append(factors[i]);
			sb.append(")*");
			sb.append("z^");
			sb.append(order()-i);
			sb.append('+');
		}
		
		sb.append('(');
		sb.append(factors[factors.length-1]);
		sb.append(")");
		
		return sb.toString();
	}
}
