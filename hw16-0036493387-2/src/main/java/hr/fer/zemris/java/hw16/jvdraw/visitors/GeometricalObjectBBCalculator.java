package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FPoly;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Point;

/**
 * This implementation of {@link GeometricalObjectVisitor} is used for calculating
 * the bounding box of objects.
 * 
 * @author Mihael Stočko
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Upper left corner x coordinate
	 */
	int rec1x = Integer.MAX_VALUE;
	
	/**
	 * Upper left corner y coordinate
	 */
	int rec1y = Integer.MAX_VALUE;
	
	/**
	 * Lower right corner x coordinate
	 */
	int rec2x = 0;
	
	/**
	 * Lower right corner y coordinate
	 */
	int rec2y = 0;
	
	/**
	 * Has the rectangle been set.
	 */
	boolean rectangleSet = false;
	
	/**
	 * Get the bounding box.
	 * 
	 * @return Rectangle object that represents the bounding box.
	 */
	public Rectangle getBoundingBox() {
		if(rectangleSet) {
			return new Rectangle(rec1x, rec1y, rec2x-rec1x, rec2y-rec1y);
		}
		
		return new Rectangle(0, 0, 800, 600);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		rectangleSet = true;
		
		int box1x = line.getX1();
		int box1y = line.getY1();
		int box2x = line.getX2();
		int box2y = line.getY2();
		
		if(box2x < box1x) {
			int tempx = box1x;
			int tempy = box1y;
			box1x = box2x;
			box1y = box2y;
			box2x = tempx;
			box2y = tempy;
		}
		
		if(box1y > box2y) {
			int diff = box1y - box2y;
			box1y -= diff;
			box2y += diff;
		}
		
		if(box1x < rec1x) {
			rec1x = box1x;
		}
		if(box1y < rec1y) {
			rec1y = box1y;
		}
		if(box2x > rec2x) {
			rec2x = box2x;
		}
		if(box2y > rec2y) {
			rec2y = box2y;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		rectangleSet = true;
		
		int box1x = circle.getC1() - (int)circle.getR();
		int box1y = circle.getC2() - (int)circle.getR();
		int box2x = circle.getC1() + (int)circle.getR();
		int box2y = circle.getC2() + (int)circle.getR();
		
		if(box1x < rec1x) {
			rec1x = box1x;
		}
		if(box1y < rec1y) {
			rec1y = box1y;
		}
		if(box2x > rec2x) {
			rec2x = box2x;
		}
		if(box2y > rec2y) {
			rec2y = box2y;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		rectangleSet = true;
		
		int box1x = filledCircle.getC1() - (int)filledCircle.getR();
		int box1y = filledCircle.getC2() - (int)filledCircle.getR();
		int box2x = filledCircle.getC1() + (int)filledCircle.getR();
		int box2y = filledCircle.getC2() + (int)filledCircle.getR();
		
		if(box1x < rec1x) {
			rec1x = box1x;
		}
		if(box1y < rec1y) {
			rec1y = box1y;
		}
		if(box2x > rec2x) {
			rec2x = box2x;
		}
		if(box2y > rec2y) {
			rec2y = box2y;
		}
	}
	
	@Override
	public void visit(FPoly fpoly) {
		int box1x = Integer.MAX_VALUE;
		int box1y = Integer.MAX_VALUE;
		int box2x = Integer.MIN_VALUE;
		int box2y = Integer.MIN_VALUE;
		
		for(Point p : fpoly.getPoints()) {
			if(p.getX() < box1x) {
				box1x = p.getX();
			}
			
			if(p.getY() < box1y) {
				box1y = p.getY();
			}
			
			if(p.getX() > box2x) {
				box2x = p.getX();
			}
			
			if(p.getY() > box2y) {
				box2y = p.getY();
			}
		}
		
		if(box1x < rec1x) {
			rec1x = box1x;
		}
		if(box1y < rec1y) {
			rec1y = box1y;
		}
		if(box2x > rec2x) {
			rec2x = box2x;
		}
		if(box2y > rec2y) {
			rec2y = box2y;
		}
	}
}
