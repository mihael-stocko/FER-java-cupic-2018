package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class contains all data required for a bar chart to be generated.
 * 
 * @author Mihael Stočko
 *
 */
public class BarChart {

	/**
	 * List of values
	 */
	List<XYValue> values;
	
	/**
	 * Title of the xAxis
	 */
	String xAxis;
	
	/**
	 * Title of the yAxis
	 */
	String yAxis;
	
	/**
	 * Minimum y value
	 */
	int yMin;
	
	/**
	 * Maximum y value
	 */
	int yMax;
	
	/**
	 * Length of one step on the yAxis
	 */
	int step;
	
	/**
	 * Constructor.
	 */
	public BarChart(List<XYValue> values, String xAxis, String yAxis, int yMin, int yMax, int step) {
		this.values = values;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.yMin = yMin;
		this.step = step;
		
		this.yMax = yMin;
		while(this.yMax < yMax) {
			this.yMax += step;
		}
	}

	/**
	 * Getter for values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for xAxis
	 */
	public String getxAxis() {
		return xAxis;
	}

	/**
	 * Getter for yAxis
	 */
	public String getyAxis() {
		return yAxis;
	}

	/**
	 * Getter for yMin
	 */
	public int getyMin() {
		return yMin;
	}
	
	/**
	 * Getter for yMax
	 */
	public int getyMax() {
		return yMax;
	}
	
	/**
	 * Getter for step
	 */
	public int getStep() {
		return step;
	}
}
