package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * This is a custom layout used for a calculator.
 * 
 * @author Mihael Stočko
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * Number of rows
	 */
	private int numOfRows = 5;
	
	/**
	 * Number of columns
	 */
	private int numOfColumns = 7;
	
	/**
	 * Array of components being held.
	 */
	Component[][] components = new JComponent[numOfRows][numOfColumns];
	
	/**
	 * Preferred component height
	 */
	private double prefComponentHeigth = 0;
	
	/**
	 * Preferred component width
	 */
	private double prefComponentWidth = 0;
	
	/**
	 * Maximum component height
	 */
	private double maxComponentHeigth = 0;
	
	/**
	 * Maximum component width
	 */
	private double maxComponentWidth = 0;
	
	/**
	 * Minimum component height
	 */
	private double minComponentHeigth = 0;
	
	/**
	 * Minimum component width
	 */
	private double minComponentWidth = 0;
	
	/**
	 * Spacing between components
	 */
	private int spacing;
	
	/**
	 * Constructor.
	 * 
	 * @param spacing Spacing between components
	 */
	public CalcLayout(int spacing) {
		this.spacing = spacing;
	}
	
	/**
	 * Default constructor. Sets the spacing to 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Adds the given component to the layout at the given position.
	 * 
	 * @param component Component to be added
	 * @param position Either an RCPosition object, or a String
	 */
	@Override
	public void addLayoutComponent(Component component, Object position) {
		if(!(position instanceof RCPosition) && !(position instanceof String)) {
			throw new CalcLayoutException("The position must be either of type RCPosition or String.");
		}
		
		RCPosition pos;
		if(position instanceof String) {
			pos = new RCPosition((String)position);
		} else {
			pos = (RCPosition)position;
		}
		
		if(!legalPosition(pos)) {
			throw new CalcLayoutException("The given position is not legal.");
		}
		
		int row = pos.getRow()-1;
		int column = pos.getColumn()-1;
		
		if(components[row][column] != null) {
			throw new CalcLayoutException("The given position already contains a component.");
		}
		
		components[row][column] = component;
	}
	
	/**
	 * Removes the given component from the layout.
	 */
	@Override
	public void removeLayoutComponent(Component arg0) {
		for(int i = 0; i < numOfRows; i++) {
			for(int j = 0; j < numOfColumns; j++) {
				if(components[i][j] == arg0) {
					components[i][j] = null;
					return;
				}
			}
		}
	}
	
	/**
	 * Arranges the components onto the surface of the container.
	 */
	@Override
	public void layoutContainer(Container c) {	
		Insets ins = c.getInsets();
		Dimension d = c.getSize();
		Rectangle r = new Rectangle(ins.left, ins.top, 
				d.width-ins.left-ins.right, d.height-ins.top-ins.bottom);
		
		int componentSizeX = (r.width-(numOfColumns-1)*spacing)/numOfColumns;
		int componentSizeY = (r.height-(numOfRows-1)*spacing)/numOfRows;
		
		for(int i = 0; i < numOfRows; i++) {
			for(int j = 0; j < numOfColumns; j++) {
				Component comp = components[i][j];
				if(comp != null) {
					
					Dimension dim;
					if(i == 0 && j == 0) {
						dim = new Dimension(5*componentSizeX+4*spacing, componentSizeY);
					} else {
						 dim = new Dimension(componentSizeX, componentSizeY);
					}
					comp.setLocation(j*componentSizeX+j*spacing, i*componentSizeY+i*spacing);
					comp.setSize(dim);
				}
			}
		}
	}
	
	/**
	 * This method goes through all components and analyzes their preferred, minimum and maximum
	 * dimensions. It stores the greatest minimum height, the greatest minimum width,
	 * the greatest preferred height, the greatest preferred width, the smallest maximum height
	 * and the smallest maximum width.
	 */
	private void analyzeComponentsSizes() {
		Dimension min = new Dimension(0, 0);
		Dimension pref = new Dimension(0, 0);
		Dimension max = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		for(int i = 0; i < numOfRows; i++) {
			for(int j = 0; j < numOfColumns; j++) {
				if(components[i][j] != null) {
					if(i == 0 && j == 0) {
						continue;	
					}
					if(components[i][j].getPreferredSize().getHeight() > pref.getHeight()) {
						pref.setSize(pref.getWidth(), components[i][j].getPreferredSize().getHeight());
					}
					if(components[i][j].getPreferredSize().getWidth() > pref.getWidth()) {
						pref.setSize(components[i][j].getPreferredSize().getWidth(), pref.getHeight());
					}
					if(components[i][j].getMinimumSize().getHeight() > min.getHeight()) {
						min.setSize(min.getWidth(), components[i][j].getMinimumSize().getHeight());
					}
					if(components[i][j].getMinimumSize().getWidth() > min.getWidth()) {
						min.setSize(components[i][j].getMinimumSize().getWidth(), min.getHeight());
					}
					if(components[i][j].getMaximumSize().getHeight() < max.getHeight()) {
						max.setSize(max.getWidth(), components[i][j].getMaximumSize().getHeight());
					}
					if(components[i][j].getMaximumSize().getWidth() < max.getWidth()) {
						max.setSize(components[i][j].getMaximumSize().getWidth(), max.getHeight());
					}
				}
			}
		}
		
		if(components[0][0] != null) {
			if(components[0][0].getPreferredSize().getHeight() > pref.getHeight()) {
				pref.setSize(pref.getWidth(), components[0][0].getPreferredSize().getHeight());
			}
			if(components[0][0].getMinimumSize().getHeight() > min.getHeight()) {
				min.setSize(min.getWidth(), components[0][0].getMinimumSize().getHeight());
			}
			if(components[0][0].getMaximumSize().getHeight() < max.getHeight()) {
				max.setSize(max.getWidth(), components[0][0].getMaximumSize().getHeight());
			}
			if((components[0][0].getPreferredSize().getWidth()-4*spacing)/5 > pref.getWidth()) {
				pref.setSize((components[0][0].getPreferredSize().getWidth()-4*spacing)/5, pref.getHeight());
			}
			if((components[0][0].getMinimumSize().getWidth()-4*spacing)/5 > min.getWidth()) {
				min.setSize((components[0][0].getMinimumSize().getWidth()-4*spacing)/5, min.getHeight());
			}
			if((components[0][0].getMaximumSize().getWidth()-4*spacing)/5 < max.getWidth()) {
				max.setSize((components[0][0].getMaximumSize().getWidth()-4*spacing)/5, max.getHeight());
			}
		}
		
		prefComponentHeigth = pref.getHeight();
		prefComponentWidth = pref.getWidth();
		minComponentHeigth = min.getHeight();
		minComponentWidth = min.getWidth();
		maxComponentHeigth = max.getHeight();
		maxComponentWidth = max.getWidth();
	}
	
	/**
	 * Returns the minimum size of the layout.
	 * 
	 * @return A Dimension object representing the minimum size of the layout.
	 */
	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		analyzeComponentsSizes();
		
		int width = (int)(minComponentWidth*numOfColumns + (numOfColumns-1)*spacing);
		int height = (int)(minComponentHeigth*numOfRows + (numOfRows-1)*spacing);
		
		return new Dimension(width, height);
	}
	
	/**
	 * Returns the preferred size of the layout.
	 * 
	 * @return A Dimension object representing the preferred size of the layout.
	 */
	@Override
	public Dimension preferredLayoutSize(Container arg0) {		
		analyzeComponentsSizes();
		
		int width = (int)(prefComponentWidth*numOfColumns + (numOfColumns-1)*spacing);
		int height = (int)(prefComponentHeigth*numOfRows + (numOfRows-1)*spacing);
		
		return new Dimension(width, height);
	}
	
	/**
	 * Returns the maximum size of the layout.
	 * 
	 * @return A Dimension object representing the maximum size of the layout.
	 */
	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		analyzeComponentsSizes();

		int width = (int)(maxComponentWidth*numOfColumns + (numOfColumns-1)*spacing);
		int height = (int)(maxComponentHeigth*numOfRows + (numOfRows-1)*spacing);
		
		return new Dimension(width, height);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container arg0) {
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		throw new CalcLayoutException("Unsupported constructor.");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0.5f;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0.5f;
	}
	
	/**
	 * Checks if the given position is legal in the context of this layout.
	 * 
	 * @param pos RCPosition object representing a position
	 * @return <code>true</code> if the given position is legal, <code>false</code> otherwise.
	 */
	private boolean legalPosition(RCPosition pos) {
		int row = pos.getRow();
		int column = pos.getColumn();
		
		if(row < 1 || row > numOfRows || column < 1 || column > numOfColumns) {
			return false;
		}
		
		if(row == 1 && column > 1 && column < 6) {
			return false;
		}
		
		return true;
	}
}
