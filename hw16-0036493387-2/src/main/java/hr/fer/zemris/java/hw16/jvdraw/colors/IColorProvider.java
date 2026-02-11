package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

/**
 * This interface models an entity that can provide colors to other objects and can have listeners.
 * 
 * @author Mihael Stočko
 *
 */
public interface IColorProvider {

	/**
	 * Getter for the current color.
	 * 
	 * @return Current color.
	 * 
	 */
	public Color getCurrentColor();
	
	/**
	 * Adds a listener to the list of listeners.
	 * 
	 * @param l Listener to be added.
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * Removes a listener from the list of listeners.
	 * 
	 * @param l Listener to be removed.
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
