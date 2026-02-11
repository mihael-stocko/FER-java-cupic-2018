package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This program demonstrates the usage of the classes ComplexPolynomial and ComplexRootedPolynomial.
 * Program arguments are not used.
 * 
 * @author Mihael Stočko
 *
 */
public class PolynomialDemo {

	/**
	 * Main method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Complex[] roots = new Complex[] {
			new Complex(1, 0),
			new Complex(3, 0),
			new Complex(-5, 0)
		};
		
		for(Complex c : roots) {
			System.out.println(c);
		}
		
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(roots);
		System.out.println(poly);
		
		ComplexPolynomial complPoly = poly.toComplexPolynom();
		System.out.println(complPoly);
	
		System.out.println();
		
		Complex[] roots2 = new Complex[] {
			new Complex(1, 1),
			new Complex(3, -2),
			new Complex(0, 3)
		};
		
		for(Complex c : roots2) {
			System.out.println(c);
		}
		
		ComplexRootedPolynomial poly2 = new ComplexRootedPolynomial(roots2);
		System.out.println(poly2);
		
		ComplexPolynomial complPoly2 = poly2.toComplexPolynom();
		System.out.println(complPoly2);
		System.out.println(complPoly2.order());
		
		System.out.println();
		
		System.out.println(poly2.apply(new Complex(2, -3)));
		System.out.println(complPoly2.apply(new Complex(2, -3)));
		
		System.out.println();
		
		System.out.println(poly2.indexOfClosestRootFor(new Complex(2, 3), 0));
		System.out.println(poly2.indexOfClosestRootFor(new Complex(2, 3), 1));
		System.out.println(poly2.indexOfClosestRootFor(new Complex(2, 3), 2));
		System.out.println(poly2.indexOfClosestRootFor(new Complex(3, -2), 0));
		
		System.out.println();
		
		Complex[] factors3 = new Complex[] {
			new Complex(7, 2),
			new Complex(2, 0),
			new Complex(5, 0),
			new Complex(1, 0)
		};
		ComplexPolynomial complPoly3 = new ComplexPolynomial(factors3);
		System.out.println(complPoly2.derive());
		System.out.println(complPoly3.derive());
		
		System.out.println();
		
		System.out.println(complPoly2.multiply(complPoly3));
		
		System.out.println();
		
		Complex[] roots4 = new Complex[] {
			Complex.ONE,
			Complex.ONE_NEG,
			Complex.IM,
			Complex.IM_NEG
		};
		
		Complex[] factors4 = new Complex[] {
			new Complex(1, 0),
			new Complex(0, 0),
			new Complex(0, 0),
			new Complex(0, 0),
			new Complex(-1, 0),
		};
		
		ComplexRootedPolynomial poly4 = new ComplexRootedPolynomial(roots4);
		System.out.println(poly4.toComplexPolynom().derive());
		
		ComplexPolynomial complPoly4 = new ComplexPolynomial(factors4);
		System.out.println(complPoly4.derive());
		
	}
}
