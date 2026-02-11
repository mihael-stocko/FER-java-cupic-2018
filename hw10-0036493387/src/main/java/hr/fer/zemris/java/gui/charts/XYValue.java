package hr.fer.zemris.java.gui.charts;

/**
 * This class represents one point of a graph.
 * 
 * @author Mihael Stočko
 *
 */
public class XYValue {
	
	/**
	 * x value
	 */
	private int x;
	
	/**
	 * y value
	 */
	private int y;
	
	/**
	 * Constructor.
	 * 
	 * @param x X value
	 * @param y Y value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for y
	 */
	public int getY() {
		return y;
	}
}
