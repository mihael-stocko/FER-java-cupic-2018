package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FPoly;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Point;

public class FPolyEditor extends GeometricalObjectEditor {

	FPoly poly;
	
	List<JTextField> fields = new LinkedList<>();
	
	public FPolyEditor(FPoly poly) {
		super();
		this.poly = poly;
		
		setLayout(new GridLayout(0, 4));
		
		for(int i = 0; i < poly.getPoints().size(); i++) {
			add(new JLabel("Point" + i + " X: ", SwingConstants.RIGHT));
			fields.add(new JTextField());
			add(fields.get(fields.size()-1));
			fields.get(fields.size()-1).setText(String.valueOf(poly.getPoints().get(i).getX()));
			add(new JLabel("Point" + i + " Y: ", SwingConstants.RIGHT));
			fields.add(new JTextField());
			add(fields.get(fields.size()-1));
			fields.get(fields.size()-1).setText(String.valueOf(poly.getPoints().get(i).getY()));
		}
	}

	@Override
	public void checkEditing() {
		if(!isConvex(poly.getPoints())) {
			throw new IllegalStateException();
		}
	}
	
	private boolean isConvex(List<Point> points) {
		boolean negative = false;
		
		if(points.get(0).getX()*points.get(1).getY() - points.get(0).getY()*points.get(1).getX() < 0) {
			negative = true;
		}
		
		for(int i = 1; i < points.size()-1; i++) {
			boolean neg = false;
			if(points.get(i).getX()*points.get(i+1).getY() - points.get(i+1).getY()*points.get(i).getX() < 0) {
				neg = true;
			}
			if(negative != neg) {
				return false;
			}
		}
		
		boolean neg = false;
		if(points.get(points.size()-1).getX()*points.get(0).getY() - points.get(0).getY()*points.get(points.size()-1).getX() < 0) {
			neg = true;
		}
		if(negative != neg) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public void acceptEditing() {
		for(int i = 0; i < poly.getPoints().size(); i++) {
			poly.getPoints().get(i).setX(Integer.parseInt(fields.get(i*2).getText()));
			poly.getPoints().get(i).setY(Integer.parseInt(fields.get(i*2+1).getText()));
		}
	}
}
