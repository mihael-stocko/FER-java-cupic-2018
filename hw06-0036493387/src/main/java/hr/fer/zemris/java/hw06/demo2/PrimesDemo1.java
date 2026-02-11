package hr.fer.zemris.java.hw06.demo2;

/**
 * This program demonstrates how the PrimesCollection object generates prime numbers.
 * 
 * @author Mihael Stočko
 *
 */
public class PrimesDemo1 {
	
	/**
	 * Main method. Arguments not used.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for(Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
