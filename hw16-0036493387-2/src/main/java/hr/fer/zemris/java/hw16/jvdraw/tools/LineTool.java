package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Line;

/**
 * This tool is used for drawing lines.
 * 
 * @author Mihael Stočko
 *
 */
public class LineTool implements Tool {

	/**
	 * Has the first point been got
	 */
	boolean firstPointGot = false;
	
	/**
	 * Is the mouse currently being dragged
	 */
	boolean drag = false;
	
	/**
	 * x coordinate of the first point
	 */
	int x;
	
	/**
	 * y coordinate of the first point
	 */
	int y;
	
	/**
	 * x coordinate of the dragging start point
	 */
	int dragX;
	
	/**
	 * y coordinate of the dragging start point
	 */
	int dragY;
	
	/**
	 * x coordinate of the current dragging position
	 */
	int xCurrent;
	
	/**
	 * y coordinate of the current dragging position
	 */
	int yCurrent;
	
	/**
	 * Drawing model
	 */
	DrawingModel dm;
	
	/**
	 * Color area
	 */
	JColorArea area;

	/**
	 * Constructor.
	 */
	public LineTool(JColorArea area) {
		super();
		this.area = area;
	}

	/**
	 * Setter for dm
	 */
	public void setDm(DrawingModel dm) {
		this.dm = dm;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		dragX = e.getX();
		dragY = e.getY();
		drag = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		drag = false;
		if(!(dragX == e.getX() && dragY == e.getY())) {
			Line line = new Line(dragX, dragY, e.getX(), e.getY());
			line.setColor(area.getSelectedColor());
			dm.add(line);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		xCurrent = e.getX();
		yCurrent = e.getY();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {		
		if(!firstPointGot) {
			firstPointGot = true;
			x = e.getX();
			y = e.getY();
		} else {
			firstPointGot = false;
			Line line = new Line(x, y, e.getX(), e.getY());
			line.setColor(area.getSelectedColor());
			dm.add(line);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		xCurrent = e.getX();
		yCurrent = e.getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		if(drag) {
			g2d.setColor(area.getSelectedColor());
			g2d.drawLine(dragX, dragY, xCurrent, yCurrent);
		} else
		if(firstPointGot) {
			g2d.setColor(area.getSelectedColor());
			g2d.drawLine(x, y, xCurrent, yCurrent);
		}
	}
}
