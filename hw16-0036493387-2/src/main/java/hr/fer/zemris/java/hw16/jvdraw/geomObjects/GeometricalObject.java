package hr.fer.zemris.java.hw16.jvdraw.geomObjects;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * This class models an abstract geometrical object. It offers support for registering listeners.
 * 
 * @author Mihael Stočko
 *
 */
public abstract class GeometricalObject {

	/**
	 * List of listeners
	 */
	List<GeometricalObjectListener> listeners = new LinkedList<>();
	
	/**
	 * Calls the appropriate visitor's method and passes itself as an argument.
	 * 
	 * @param v GeometricalObjectVisitor object
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Adds a listener to the list of listeners.
	 * 
	 * @param l New listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "null cannot be added to the list of listeners.");
		listeners.add(l);
	}
	
	/**
	 * Removes a listener from the list of listeners.
	 * 
	 * @param l Listener to be removed
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "null cannot be removed from the list of listeners.");
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners that a change has occured.
	 */
	public void notifyListenersChanged() {
		for(GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
	
	/**
	 * Creates an editor for the concrete object.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
}
