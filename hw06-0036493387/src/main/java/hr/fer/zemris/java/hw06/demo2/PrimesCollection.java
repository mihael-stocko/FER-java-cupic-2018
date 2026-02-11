package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class generates a requested amount of prime numbers.
 * 
 * @author Mihael Stočko
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * How many primes will be created.
	 */
	private int howMany;

	/**
	 * Constructor.
	 * 
	 * @param howMany
	 */
	public PrimesCollection(int howMany) {
		super();
		this.howMany = howMany;
	}
	
	/**
	 * Returns an iterator.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator(howMany);
	}
	
	/**
	 * An implementation of the iterator used for generating prime numbers.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private class PrimesCollectionIterator implements Iterator<Integer> {
		
		/**
		 * How many more primes should be created.
		 */
		private int primesLeft;
		
		/**
		 * Most recently generated prime.
		 */
		private int currentPrime;
		
		/**
		 * Constructor. Takes the number of primes that should be created as an argument.
		 * @param primesLeft
		 */
		public PrimesCollectionIterator(int primesLeft) {
			super();
			this.primesLeft = primesLeft;
			currentPrime = 1;
		}

		/**
		 * Returns <code>true</code> if there are still prime numbers pending.
		 */
		@Override
		public boolean hasNext() {
			return primesLeft > 0;
		}
		
		/**
		 * Generates a new prime number and returns it.
		 * @throws NoSuchElementException
		 */
		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException("There are no more prime numbers.");
			}
			
			while(true) {
				currentPrime++;
				if(isPrime(currentPrime)) {
					break;
				}
			}
			
			primesLeft--;
			return currentPrime;
		}
		
		/**
		 * Checks whether the given number is prime.
		 * 
		 * @param number
		 * @return <code>true</code> if the argument is prime, <code>false</code> otherwise.
		 */
		private boolean isPrime(int number) {
			if(number == 1) {
				return false;
			}
			
			if(number == 2 || number == 3) {
				return true;
			}
			
			for(int i = 2; i <= number/2; i++) {
				if(number % i == 0) {
					return false;
				}
			}
			
			return true;
		}
	}
}
