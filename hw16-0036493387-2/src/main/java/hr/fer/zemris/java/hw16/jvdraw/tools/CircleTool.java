package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Circle;

/**
 * This tool is used for drawing circles.
 * 
 * @author Mihael Stočko
 *
 */
public class CircleTool implements Tool {

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
	public CircleTool(JColorArea area) {
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
			Circle circle = new Circle(dragX, dragY, 
					Math.sqrt(Math.pow(e.getX()-dragX, 2) + Math.pow(e.getY()-dragY, 2)));
			circle.setColor(area.getSelectedColor());
			dm.add(circle);
		}
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
			Circle circle = new Circle(x, y, Math.sqrt(Math.pow(e.getX()-x, 2) + Math.pow(e.getY()-y, 2)));
			circle.setColor(area.getSelectedColor());
			dm.add(circle);
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
	public void mouseDragged(MouseEvent e) {
		xCurrent = e.getX();
		yCurrent = e.getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(area.getSelectedColor());
		if(drag) {
			int r = (int)Math.sqrt(Math.pow(xCurrent-dragX, 2) + Math.pow(yCurrent-dragY, 2));
			int pointX = dragX-r;
			int pointY =dragY-r;
			g2d.drawOval(pointX, pointY, r*2, r*2);
		} else if(firstPointGot) {
			int r = (int)Math.sqrt(Math.pow(xCurrent-x, 2) + Math.pow(yCurrent-y, 2));
			int pointX = x-r;
			int pointY =y-r;
			g2d.drawOval(pointX, pointY, r*2, r*2);
		} 
	}

}
