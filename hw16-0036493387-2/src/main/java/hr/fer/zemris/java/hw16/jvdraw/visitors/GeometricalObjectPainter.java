package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FPoly;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Point;

/**
 * This implementation of {@link GeometricalObjectVisitor} is used for painting geometrical
 * objects. A {@link Graphics2D} object must be provided.
 * 
 * @author Mihael Stočko
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics2D object
	 */
	Graphics2D g;

	/**
	 * Constructor.
	 */
	public GeometricalObjectPainter(Graphics2D g) {
		super();
		this.g = g;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		g.setColor(line.getColor());
		g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		g.setColor(circle.getColor());
		int pointX = (int)(circle.getC1()-circle.getR());
		int pointY = (int)(circle.getC2()-circle.getR());
		g.drawOval(pointX, pointY, (int)circle.getR()*2, (int)circle.getR()*2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		g.setColor(filledCircle.getColor1());
		int pointX = (int)(filledCircle.getC1()-filledCircle.getR());
		int pointY = (int)(filledCircle.getC2()-filledCircle.getR());
		g.drawOval(pointX, pointY, (int)filledCircle.getR()*2, (int)filledCircle.getR()*2);
		g.setColor(filledCircle.getColor2());
		g.fillOval(pointX, pointY, (int)filledCircle.getR()*2, (int)filledCircle.getR()*2);
	}
	
	@Override
	public void visit(FPoly fpoly) {
		g.setColor(Color.RED);
		List<Point> points = fpoly.getPoints();
		if(points.size() > 2) {
			for(int i = 0; i < points.size()-1; i++) {
				g.drawLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
			}
			
			g.drawLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), 
					points.get(0).getX(), points.get(0).getY());
			
			
		}
		
		if(points.size() > 1) {
			g.setColor(fpoly.getColor2());
			int[] xCoor = new int[points.size()];
			int[] yCoor = new int[points.size()];
			
			for(int i = 0; i < points.size(); i++) {
				xCoor[i] = points.get(i).getX();
				yCoor[i] = points.get(i).getY();
			}
			
			g.fillPolygon(xCoor, yCoor, points.size());
		}
	}
}
