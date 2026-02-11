package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.GeometricalObject;

/**
 * This interface defines a drawing model. A drawing model has a list of geometrical objects
 * and a list of listeners. When a change in its content occurs, it notifies its listeners.
 * 
 * @author Mihael Stočko
 *
 */
public interface DrawingModel {

	/**
	 * Gets the number of geometrical objects.
	 */
	public int getSize();
	
	/**
	 * Gets object at the given index.
	 * 
	 * @param index Index of the object
	 * @return Object at the given index
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * Adds the given object to the list.
	 * 
	 * @param object Object to be added.
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Removes the given object from the list.
	 * 
	 * @param object Object to be removed.
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * Changes the position of the given object by adding its position to the given offset.
	 * 
	 * @param object Object whose position is to be updated.
	 */
	public void changeOrder(GeometricalObject object, int offset);
	
	/**
	 * Adds the given listener to the list of listeners.
	 * 
	 * @param l Listener to be added.
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes the given listener from the list of listeners.
	 * 
	 * @param l Listener to be removed.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
