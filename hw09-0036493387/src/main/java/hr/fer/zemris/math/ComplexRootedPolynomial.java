package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a rooted complex polynomial. It offers methods for some operations
 * on the given polynomial.
 * 
 * @author Mihael Stočko
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Roots of the polynomial
	 */
	Complex[] roots;
	
	/**
	 * Constructor. It takes an arbitrary positive number of roots for the polynomial.
	 * 
	 * @param roots
	 * @throws IllegalArgumentException
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if(roots.length < 1) {
			throw new IllegalArgumentException("At least one root must be provided.");
		}
		this.roots = roots;
	}
	
	/**
	 * Evaluates this polynomial at the given parameter z.
	 * 
	 * @param z parameter for evaluation
	 * @return Value of the polynomial for the given parameter.
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "The polynomial cannot be evaluated with null.");
		
		Complex result = Complex.ONE;
		for(Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		
		return result;
	}
	
	/**
	 * Converts this rooted polynomial to an object of the type ComplexPolynomial.
	 * 
	 * @return a new ComplexPolynomial object.
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] result = new Complex[roots.length+1];
		for(int i = 0; i < result.length; i++) {
			result[i] = Complex.ZERO;
		}
		
		result[0] = roots[0].negate();
		result[1] = Complex.ONE;
		
		for(int i = 1; i < roots.length; i++) {
			 Complex[] temp = new Complex[i+1];
			 for(int j = 0; j < temp.length; j++) {
				 temp[j] = result[j].multiply(roots[i].negate());
			 }
			 for(int j = i+1; j > 0; j--) {
				 result[j] = result[j-1];
			 }
			 result[0] = Complex.ZERO;
			 
			 for(int j = 0; j < temp.length; j++) {
				 result[j] = result[j].add(temp[j]);
			 }
		}
		
		Complex[] resultReversed = new Complex[roots.length+1];
		for(int i = 0; i < result.length; i++) {
			resultReversed[resultReversed.length-1-i] = result[i];
		}
		
		return new ComplexPolynomial(resultReversed);
	}
	
	/**
	 * A string representation of this polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < roots.length-1; i++) {
			sb.append("(z-(");
			sb.append(roots[i]);
			sb.append("))");
			sb.append("*");
		}
		
		sb.append("(z-(");
		sb.append(roots[roots.length-1]);
		sb.append("))");
		
		return sb.toString();
	}

	/**
	 * For the given complex number z returns the index of the closest root within the given threshold.
	 * If there is no such root, returns -1.
	 * 
	 * @param z Complex number
	 * @param threshold Threshold that defines the scope of the search.
	 * @return Index of the closest root.
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		Objects.requireNonNull(z, "Cannot calculate closest root for null.");
		
		int index = 1;
		double minDistance = z.sub(roots[0]).module();
		
		for(int i = 1; i < roots.length; i++) {
			double distance = z.sub(roots[i]).module();
			if(distance < minDistance) {
				index = i+1;
				minDistance = distance;
			}
		}
		
		return minDistance <= threshold ? index : -1;
	}
}
