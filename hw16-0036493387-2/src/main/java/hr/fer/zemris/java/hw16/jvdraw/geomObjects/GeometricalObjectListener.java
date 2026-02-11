package hr.fer.zemris.java.hw16.jvdraw.geomObjects;

/**
 * This interface represents a geometrical object listener. It is notified when a change occurs
 * on an object.
 * 
 * @author Mihael Stočko
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Called when an object that is being listened to is changed.
	 * 
	 * @param o Object that has been changed.
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
