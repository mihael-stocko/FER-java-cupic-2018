package hr.fer.zemris.java.hw06.demo2;

/**
 * This program demonstrates iterating through PrimesCollection with two iterators at the same time.
 * 
 * @author Mihael Stočko
 *
 */
public class PrimesDemo2 {

	/**
	 * Main method. Arguments not used.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
			for(Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: "+prime+", "+prime2);
			}
		}
	}
}
