package hr.fer.zemris.java.gui.layouts;

/**
 * This class represents a position in the CalcLayout.
 * 
 * @author Mihael Stočko
 *
 */
public class RCPosition {

	/**
	 * Index of the row in the layout.
	 */
	private int row;
	
	/**
	 * Index of the column in the layout.
	 */
	private int column;
	
	/**
	 * Constructor. Takes the row index and the column index separated by a comma.
	 * 
	 */
	public RCPosition(String input) {
		String[] strings = input.split(",");
		try {
			row = Integer.parseInt(strings[0]);
			column = Integer.parseInt(strings[1]);
		} catch(Exception e) {
			throw new CalcLayoutException("The position given as a string cannot be parsed.");
		}
	}
	
	/**
	 * Constructor. Takes the row index and the column index.
	 * 
	 * @param row Index of the row
	 * @param column Index of the column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Getter for the row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for the column
	 */
	public int getColumn() {
		return column;
	}
}
