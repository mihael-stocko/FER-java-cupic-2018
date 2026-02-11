package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

/**
 * This interface models a color change listener. The listener is notified when a color changes.
 * 
 * @author Mihael Stočko
 *
 */
public interface ColorChangeListener {

	/**
	 * Called when the change in color occurs.
	 * 
	 * @param source Source of the color change
	 * @param oldColor Old color
	 * @param newColor New color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
