package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class representing an integer storage that can be observed using the Observer pattern.
 * 
 * @author Mihael Stočko
 *
 */
public class IntegerStorage {
	
	/**
	 * Value currently stored in the storage.
	 */
	private int value;
	
	/**
	 * List of active observers.
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Observers that are to be removed at the beginning of making the next change.
	 */
	private List<IntegerStorageObserver> toBeRemoved;
	
	/**
	 * Constructor. Accepts an initial value for the storage.
	 * 
	 * @param initialValue Initial value for the storage.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Adds the given observer to the list of observers.
	 * 
	 * @param observer
	 * @throws NullPointerException
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer, "Cannot add null to observers.");
		
		if(observers == null) {
			observers = new ArrayList<>();
		}
		
		observers.add(observer);
	}
	
	/**
	 * Removes the given observer from the list of observers.
	 * 
	 * @param observer
	 * @throws NullPointerException
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer, "Cannot remove null from observers.");
		
		if(toBeRemoved == null) {
			toBeRemoved = new ArrayList<>();
		}
		
		toBeRemoved.add(observer);
	}
	
	/**
	 * Removes all observers from the list of observers.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Getter for the value stored.
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the stored value to the one given.
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		if(this.value!=value) {
			if(toBeRemoved != null) {
				for(IntegerStorageObserver observer : toBeRemoved) {
					observers.remove(observer);
				}
				toBeRemoved = null;
			}
			this.value = value;
			if(observers!=null) {
				for(IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
