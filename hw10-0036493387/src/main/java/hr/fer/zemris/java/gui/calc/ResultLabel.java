package hr.fer.zemris.java.gui.calc;

import javax.swing.JLabel;

/**
 * This component is a screen for displaying the results of calculations on the calculator.
 * 
 * @author Mihael Stočko
 *
 */
public class ResultLabel extends JLabel implements CalcValueListener {
	
	public static final long serialVersionUID = 1L;

	/**
	 * Used for comparing floating point numbers.
	 */
	public static final double delta = 1e-9;
	
	/**
	 * Constructor. Takes the initial text to be displayed. 
	 * 
	 * @param initialText What will be shown when the calculator is first powered up.
	 */
	public ResultLabel(String initialText) {
		setText(initialText);
	}
	
	/**
	 * This is being called whenever the value of the calculator model this component is a listener of
	 * is changed.
	 */
	@Override
	public void valueChanged(CalcModel model) {
		Double newValue = model.getValue();
		
		if(newValue.toString().endsWith(".0")) {
			setText(newValue.toString().substring(0, newValue.toString().length()-2));
		} else {
			setText(newValue.toString());
		}
	}
}
