package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FilledCircle;

/**
 * This tool is used for drawing filled circles.
 * 
 * @author Mihael Stočko
 *
 */
public class FCircleTool implements Tool {

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
	 * Foreground color area
	 */
	JColorArea area1;
	
	/**
	 * Background color area
	 */
	JColorArea area2;

	/**
	 * Constructor.
	 */
	public FCircleTool(JColorArea area1, JColorArea area2) {
		super();
		this.area1 = area1;
		this.area2 = area2;
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
			FilledCircle fCircle = new FilledCircle(dragX, dragY, 
					Math.sqrt(Math.pow(e.getX()-dragX, 2) + Math.pow(e.getY()-dragY, 2)));
			fCircle.setColor1(area1.getSelectedColor());
			fCircle.setColor2(area2.getSelectedColor());
			dm.add(fCircle);
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
			FilledCircle fCircle = new FilledCircle(x, y, 
					Math.sqrt(Math.pow(e.getX()-x, 2) + Math.pow(e.getY()-y, 2)));
			fCircle.setColor1(area1.getSelectedColor());
			fCircle.setColor2(area2.getSelectedColor());
			dm.add(fCircle);
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
		g2d.setColor(area1.getSelectedColor());
		if(drag) {
			int r = (int)Math.sqrt(Math.pow(xCurrent-dragX, 2) + Math.pow(yCurrent-dragY, 2));
			int pointX = dragX-r;
			int pointY = dragY-r;
			g2d.drawOval(pointX, pointY, r*2, r*2);
			g2d.setColor(area2.getSelectedColor());
			g2d.fillOval(pointX, pointY, r*2, r*2);
		} else if(firstPointGot) {
			int r = (int)Math.sqrt(Math.pow(xCurrent-x, 2) + Math.pow(yCurrent-y, 2));
			int pointX = x-r;
			int pointY = y-r;
			g2d.drawOval(pointX, pointY, r*2, r*2);
			g2d.setColor(area2.getSelectedColor());
			g2d.fillOval(pointX, pointY, r*2, r*2);
		}
	}

}
