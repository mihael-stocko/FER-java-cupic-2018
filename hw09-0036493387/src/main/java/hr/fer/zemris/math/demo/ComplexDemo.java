package hr.fer.zemris.math.demo;

import java.util.List;

import hr.fer.zemris.math.Complex;

/**
 * This program demonstrates how roots of a complex number are calculated using the class Complex.
 * Program arguments are not used.
 * 
 * @author Mihael Stočko
 *
 */
public class ComplexDemo {
	
	/**
	 * Main method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(2.5, -3);
		Complex c3 = c1.multiply(c2);
		List<Complex> roots = c3.root(5);
		for(Complex root : roots) {
			System.out.println(root);
		}
	}
}
