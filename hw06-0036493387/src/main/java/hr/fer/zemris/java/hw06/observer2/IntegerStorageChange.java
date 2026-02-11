package hr.fer.zemris.java.hw06.observer2;

import java.util.Objects;

/**
 * This class is a representation of a change performed on the object of IntegerStorage.
 * 
 * @author Mihael Stočko
 *
 */
public class IntegerStorageChange {

	/**
	 * IntegerStorage object.
	 */
	private IntegerStorage integerStorage;
	
	/**
	 * The value that the IntegerStorage object had before the change.
	 */
	private int previousValue;
	
	/**
	 * The new value of the IntegerStorage object.
	 */
	private int currentValue;
	
	/**
	 * Constructor.
	 * 
	 * @param integerStorage
	 * @param previousValue
	 * @param currentValue
	 * @throws NullPointerException
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int previousValue, int currentValue) {
		Objects.requireNonNull(integerStorage, "IntegerStorage cannot be null.");
		this.integerStorage = integerStorage;
		this.previousValue = previousValue;
		this.currentValue = currentValue;
	}

	/**
	 * Getter for integerStorage object.
	 * 
	 * @return
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	/**
	 * Getter for the previous value.
	 * 
	 * @return
	 */
	public int getPreviousValue() {
		return previousValue;
	}

	/**
	 * Getter for the current value.
	 * 
	 * @return
	 */
	public int getCurrentValue() {
		return currentValue;
	}
}
