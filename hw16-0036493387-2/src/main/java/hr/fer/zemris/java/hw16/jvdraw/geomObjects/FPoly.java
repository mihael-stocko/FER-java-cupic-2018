package hr.fer.zemris.java.hw16.jvdraw.geomObjects;

import java.awt.Color;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.editors.FPolyEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

public class FPoly extends GeometricalObject {

	List<Point> points;
	
	Color color1;
	
	Color color2;
	
	
	
	public Color getColor1() {
		return color1;
	}



	public void setColor1(Color color1) {
		this.color1 = color1;
	}



	public Color getColor2() {
		return color2;
	}



	public void setColor2(Color color2) {
		this.color2 = color2;
	}



	public FPoly(List<Point> points) {
		super();
		this.points = points;
	}
	
	

	public List<Point> getPoints() {
		return points;
	}



	public void setPoints(List<Point> points) {
		this.points = points;
	}



	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		
		return new FPolyEditor(this);
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
}
