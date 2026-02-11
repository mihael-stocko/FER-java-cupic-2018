package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.GeometricalObjectListener;

/**
 * A concrete implementation of the {@link DrawingModel} interface.
 * 
 * @author Mihael Stočko
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * List of {@link GeometricalObject} objects
	 */
	List<GeometricalObject> objects = new LinkedList<>();
	
	/**
	 * List of listeners
	 */
	List<DrawingModelListener> listeners = new LinkedList<>();
	
	/**
	 * Foreground color
	 */
	Color foregroundColor;
	
	/**
	 * Background color
	 */
	Color backgroundColor;
	
	/**
	 * Getter for foregroundColor
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Setter for foregroundColor
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	/**
	 * Getter for backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Setter for backgroundColor
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return objects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getObject(int index) {
		if(index < 0 || index > objects.size()-1) {
			throw new IndexOutOfBoundsException("Index out of bounds, was " + index + ".");
		}
		
		return objects.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(GeometricalObject object) {
		Objects.requireNonNull(object, "Cannot add null to the list of geometrical objects.");
		objects.add(object);
		object.addGeometricalObjectListener(new GeometricalObjectListener() {
			
			@Override
			public void geometricalObjectChanged(GeometricalObject o) {
				notifyChanged(objects.indexOf(o));
			}
		});
		notifyAdded(objects.size()-1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(GeometricalObject object) {
		Objects.requireNonNull(object, "Cannot remove null from the list of geometrical objects.");
		objects.remove(object);
		notifyRemoved(objects.indexOf(object));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		Objects.requireNonNull(object, "null is not a valid argument.");
		int pos = objects.indexOf(object);
		pos += offset;
		objects.remove(object);
		objects.add(pos, object);
		notifyChanged(pos);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "null cannot be added to the list of listeners.");
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "null cannot be removed from the list of listeners.");
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners that an object has been added at the given index.
	 * 
	 * @param index Index of the added object.
	 */
	private void notifyAdded(int index) {
		for(DrawingModelListener l : listeners) {
			l.objectsAdded(this, index, index);
		}
	}
	
	/**
	 * Notifies all listeners that an object has been changed at the given index.
	 * 
	 * @param index Index of the changed object.
	 */
	private void notifyChanged(int index) {
		for(DrawingModelListener l : listeners) {
			l.objectsChanged(this, index, index);
		}
	}
	
	/**
	 * Notifies all listeners that an object has been removed from the given index.
	 * 
	 * @param index Index of the removed object.
	 */
	private void notifyRemoved(int index) {
		for(DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index);
		}
	}
}
