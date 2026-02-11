package hr.fer.zemris.java.hw06.observer1;

/**
 * This interface is used in the Observer pattern. The class that implements this must provide
 * a method that is called when a change occurs on the object that is being observed.
 * 
 * @author Mihael Stočko
 *
 */
public interface IntegerStorageObserver {

	/**
	 * This method is called when the object being observed is changed.
	 * 
	 * @param istorage Object being observed
	 */
	public void valueChanged(IntegerStorage istorage);
}
