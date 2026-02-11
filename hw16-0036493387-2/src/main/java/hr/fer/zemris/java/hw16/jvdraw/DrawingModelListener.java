package hr.fer.zemris.java.hw16.jvdraw;

/**
 * This listener gets notifications when objects are added to, removed from, or changed in
 * the drawing model.
 * 
 * @author Mihael Stočko
 *
 */
public interface DrawingModelListener {

	/**
	 * This is called when objects are added to the drawing model.
	 * 
	 * @param source Drawing model
	 * @param index0 index of the first object
	 * @param index1 index of the last object
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * This is called when objects are removed from the drawing model.
	 * 
	 * @param source Drawing model
	 * @param index0 index of the first object
	 * @param index1 index of the last object
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * This is called when objects in the drawing model are changed.
	 * 
	 * @param source Drawing model
	 * @param index0 index of the first object
	 * @param index1 index of the last object
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
