package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This interface represents a single tool for drawing objects.
 * 
 * @author Mihael Stočko
 *
 */
public interface Tool {
	/**
	 * Called when mouse is pressed.
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * Called when mouse is released.
	 */
	public void mouseReleased(MouseEvent e);
	
	/**
	 * Called when mouse is clicked.
	 */
	public void mouseClicked(MouseEvent e);
	
	/**
	 * Called when mouse is moved.
	 */
	public void mouseMoved(MouseEvent e);
	
	/**
	 * Called when mouse is dragged.
	 */
	public void mouseDragged(MouseEvent e);
	
	/**
	 * This method paints of the given {@link Graphics2D} objects.
	 * 
	 * @param g2d Graphics2D object
	 */
	public void paint(Graphics2D g2d);
}