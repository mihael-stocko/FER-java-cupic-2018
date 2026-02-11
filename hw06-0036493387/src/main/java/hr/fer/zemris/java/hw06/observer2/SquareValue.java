package hr.fer.zemris.java.hw06.observer2;

import java.util.Objects;

/**
 * An implementation of the interface IntegerStorageObserver. It squares the value of the
 * observed object. 
 * 
 * @author Mihael Stočko
 *
 */
public class SquareValue implements IntegerStorageObserver {
	
	/**
	 * This is called when a change occurs. It squares the value of the object being observed.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		Objects.requireNonNull(istorage, "Passed IntegerStorage cannot be null.");
		
		System.out.println("Provided new value: " + istorage.getCurrentValue() + 
				", square is " + Math.pow(istorage.getCurrentValue(), 2));
	}
}
