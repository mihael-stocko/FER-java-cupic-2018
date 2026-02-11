package hr.fer.zemris.java.hw16.jvdraw.editors;

import javax.swing.JPanel;

/**
 * This interface models a geometrical object editor. It provides methods for checking whether
 * the changes are legal and applying the changes.
 * 
 * @author Mihael Stočko
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Checks whether the changes are legal. If not, throws an exception.
	 */
	public abstract void checkEditing();
	
	/**
	 * Applies the changes to the object.
	 */
	public abstract void acceptEditing();
}
