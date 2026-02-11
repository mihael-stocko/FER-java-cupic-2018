package hr.fer.zemris.java.gui.calc.Calculator;

import javax.swing.JButton;

/**
 * This component is a button used for appending a single digit to the current 
 * value of the calculator.
 * 
 * @author Mihael Stočko
 *
 */
public class ButtonDigit extends JButton {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * Digit to be inserted.
	 */
	int digit;
	
	/**
	 * Constructor. Takes a digit to insert.
	 * 
	 * @param digit Integer to be inserted. Must be between 0 and 9.
	 */
	public ButtonDigit(int digit) {
		if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException("The given argument is not a single digit.");
		}
		
		this.digit = digit;
		setText(Integer.toString(digit));
	}
	
	/**
	 * Getter for digit.
	 */
	public int getDigit() {
		return digit;
	}
}
