package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * The canvas for drawing geometrical objects.
 * 
 * @author Mihael Stočko
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Drawing model
	 */
	DrawingModel dm;
	
	/**
	 * Current tool
	 */
	Tool tool;
	
	/**
	 * Constructor
	 */
	public JDrawingCanvas(DrawingModel dm) {
		super();
		this.dm = dm;
		dm.addDrawingModelListener(this);
	}

	/**
	 * Getter for tool
	 */
	public Tool getTool() {
		return tool;
	}

	/**
	 * Setter for tool
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getMaximumSize().width, getMaximumSize().height);
		
		g.setColor(Color.BLACK);
		GeometricalObjectVisitor v = new GeometricalObjectPainter((Graphics2D)g);
		
		int n = dm.getSize();
		for(int i = 0; i < n; i++) {
			dm.getObject(i).accept(v);
		}
		
		tool.paint((Graphics2D)g);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}
}
