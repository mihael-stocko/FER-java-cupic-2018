package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FPoly;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Point;

public class FPolyTool implements Tool {
	
	List<Point> points = new LinkedList<>();
	
	boolean firstGot = false;
	
	DrawingModel dm;

	int xCurr;
	int yCurr;
	
	JColorArea area1;
	JColorArea area2;
	
	public FPolyTool(JColorArea area1, JColorArea area2) {
		super();
		this.area1 = area1;
		this.area2 = area2;
	}

	public void setDm(DrawingModel dm) {
		this.dm = dm;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		if(!firstGot) {
			firstGot = true;
			points.add(new Point(e.getX(), e.getY()));
		} else {
			Point newPoint = new Point(e.getX(), e.getY());
			if(!distanceTooSmall(newPoint, points.get(points.size()-1))) {
				points.add(newPoint);
				if(points.size() >= 4) {
					if(!isConvex()) {
						points.remove(points.size()-1);
					}
				}
				
			} else {
				List<Point> newPoints = new LinkedList<>(points);
				FPoly fpoly = new FPoly(newPoints);
				fpoly.setColor1(area1.getSelectedColor());
				fpoly.setColor2(area2.getSelectedColor());
				if(points.size() < 3) {
					System.out.println("Manje od 3 ne može.");
					firstGot = false;
					points.clear();
				} else {
					dm.add(fpoly);
					firstGot = false;
					points.clear();
				}
				
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		xCurr = e.getX();
		yCurr = e.getY();
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(area1.getSelectedColor());
		if(firstGot) {
			for(int i = 0; i < points.size()-1; i++) {
				g2d.drawLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
			}
				
			g2d.drawLine(xCurr, yCurr, points.get(points.size()-1).getX(), points.get(points.size()-1).getY());
			g2d.drawLine(xCurr, yCurr, points.get(0).getX(), points.get(0).getY());
		}
		
		if(points.size() > 1) {
			g2d.setColor(area2.getSelectedColor());
			int[] xCoor = new int[points.size()+1];
			int[] yCoor = new int[points.size()+1];
			
			for(int i = 0; i < points.size(); i++) {
				xCoor[i] = points.get(i).getX();
				yCoor[i] = points.get(i).getY();
			}
			
			xCoor[xCoor.length-1] = xCurr;
			yCoor[yCoor.length-1] = yCurr;
			g2d.fillPolygon(xCoor, yCoor, points.size()+1);
		}
		
	}
	
	private boolean distanceTooSmall(Point p1, Point p2) {
		double distance = Math.sqrt(Math.pow(p2.getX() - (double)p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
		System.out.println(distance);
		return distance < 3;
	}
	
	private boolean isConvex() {
		boolean negative = false;
		
		if((points.get(0).getX()*points.get(1).getX() + points.get(0).getY()*points.get(1).getY()) < 0) {
			negative = true;
		}
		
		for(int i = 1; i < points.size()-1; i++) {
			boolean neg = false;
			if(points.get(i).getX()*points.get(i+1).getX() + points.get(i).getY()*points.get(i+1).getY() < 0) {
				neg = true;
			}
			if(negative != neg) {
				return false;
			}
		}
		
		boolean neg = false;
		if(points.get(points.size()-1).getX()*points.get(0).getX() + points.get(points.size()-1).getY()*points.get(0).getY() < 0) {
			neg = true;
		}
		if(negative != neg) {
			return false;
		}
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
