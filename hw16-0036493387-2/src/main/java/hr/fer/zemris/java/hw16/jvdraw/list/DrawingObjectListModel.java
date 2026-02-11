package hr.fer.zemris.java.hw16.jvdraw.list;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FPoly;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Line;

/**
 * This list model contains string representations of objects in the {@link DrawingModel}.
 * It mirrors the current state in the drawing model. It offers support for manipulating
 * objects in the drawing model.
 * 
 * @author Mihael Stočko
 *
 */
public class DrawingObjectListModel extends AbstractListModel<String> implements DrawingModelListener {

	public static final long serialVersionUID = 1L;
	
	/**
	 * List of objects.
	 */
	List<String> list = new LinkedList<>();
	
	/**
	 * Drawing model
	 */
	DrawingModel dm;
	
	/**
	 * Constructor
	 */
	public DrawingObjectListModel(DrawingModel dm) {
		super();
		this.dm = dm;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return list.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getElementAt(int arg0) {
		if(arg0 < 0 || arg0 > list.size()-1) {
			throw new IndexOutOfBoundsException();
		}
		
		return list.get(arg0);
		
	}
	
	/**
	 * Removes the object at the given index from the drawing model.
	 * 
	 * @param index Index of the object to be removed.
	 */
	public void remove(int index) {
		if(index < 0 || index > list.size()-1) {
			throw new IndexOutOfBoundsException();
		}
		
		dm.remove(dm.getObject(index));
		list.remove(index);
		fireIntervalRemoved(this, index, index);
	}
	
	/**
	 * Moves the object at the given index one place up.
	 * 
	 * @param index Index of the object to be moved up.
	 */
	public void moveUp(int index) {
		if(index < 0 || index > list.size()-1) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index > 0) {
			dm.changeOrder(dm.getObject(index), -1);
		}
	}
	
	/**
	 * Moves the object at the given index one place down.
	 * 
	 * @param index Index of the object to be moved down.
	 */
	public void moveDown(int index) {
		if(index < 0 || index > list.size()-1) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index < list.size()-1) {
			dm.changeOrder(dm.getObject(index), 1);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {	
		Object o = source.getObject(index0);
		list.add(prepareString(o));
		fireIntervalAdded(this, index0, index1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		list.clear();
		int n = dm.getSize();
		for(int i = 0; i < n; i++) {
			list.add(prepareString(dm.getObject(i)));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {}
	
	private String prepareString(Object o) {
		String s;
		
		if(o instanceof Line) {
			Line line = (Line)o;
			s = "Line (" + line.getX1() + "," + line.getY1() + ")-(" + 
				line.getX2() + "," + line.getY2() + ")";
		} else if(o instanceof Circle) {
			Circle circle = (Circle)o;
			s = "Circle (" + circle.getC1() + "," + circle.getC2() + "), " + (int)circle.getR();
		} else if(o instanceof FilledCircle) {
			FilledCircle filledCirle = (FilledCircle)o;
			s = "Filled circle (" + filledCirle.getC1() + "," + filledCirle.getC2() + "), " + 
					(int)filledCirle.getR() + ", " + String.format("#%02x%02x%02x", 
							filledCirle.getColor2().getRed(), filledCirle.getColor2().getGreen(), 
							filledCirle.getColor2().getBlue());
		} else if(o instanceof FPoly) {
			FPoly fPoly = (FPoly)o;
			s = "FPoly";
		} else {
			throw new IllegalArgumentException();
		}
		
		return s;
	}
}
