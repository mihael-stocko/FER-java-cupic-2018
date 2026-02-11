package hr.fer.zemris.java.hw06.observer1;

/**
 * An implementation of the interface IntegerStorageObserver. It counts how many changes have
 * occurred on the observed object since the beginning of observing.
 * 
 * @author Mihael Stočko
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Counter of changes
	 */
	private int counter = 0;
	
	/**
	 * This is called when a change occurs. It increments the counter by 1 and prints the
	 * current number of changes.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}
}
