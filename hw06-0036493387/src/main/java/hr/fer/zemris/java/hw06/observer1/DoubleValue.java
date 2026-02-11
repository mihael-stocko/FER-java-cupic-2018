package hr.fer.zemris.java.hw06.observer1;

/**
 * An implementation of the interface IntegerStorageObserver. It doubles the value of the
 * observed object for a specified number of times and then stops observing. 
 * 
 * @author Mihael Stočko
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of changes this class will observe.
	 */
	private int maxChanges;
	
	/**
	 * Constructor.
	 * 
	 * @param maxChanges Number of changes this class will observe.
	 */
	public DoubleValue(int maxChanges) {
		super();
		if(maxChanges <= 0) {
			throw new IllegalArgumentException("The argument for DoubleValue must be at least 1, was " + 
		maxChanges);
		}
		this.maxChanges = maxChanges;
	}

	/**
	 * This is called when a change occurs. It prints the doubled value of the object being observed.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + istorage.getValue()*2);
		if(--maxChanges == 0) {
			istorage.removeObserver(this);
		}
	}
}
