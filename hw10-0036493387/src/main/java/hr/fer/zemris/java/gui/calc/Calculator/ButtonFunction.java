package hr.fer.zemris.java.gui.calc.Calculator;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

/**
 * This component is a button used for computing unary operations.
 * It contains two references to DoubleUnaryOperator objects.
 * Depending on the button Inv, one or the other is being applied.
 * 
 * @author Mihael Stočko
 *
 */
public class ButtonFunction extends JButton {
	
	public static final long serialVersionUID = 1L;

	/**
	 * The main function of the button.
	 */
	DoubleUnaryOperator oper1;
	
	/**
	 * The alternative function of the button.
	 */
	DoubleUnaryOperator oper2;
	
	/**
	 * Constructor
	 * 
	 * @param oper1 Main function
	 * @param oper2 Alternative function
	 * @param text Text of the button
	 */
	public ButtonFunction(DoubleUnaryOperator oper1, DoubleUnaryOperator oper2, String text) {
		this.oper1 = oper1;
		this.oper2 = oper2;
		setText(text);
	}
	
	/**
	 * Executes the operation.
	 * Depending on the state of the button Inv, one or the other is being applied.
	 * 
	 * @return Result of the operation.
	 */
	public double execute(double operand, boolean inv) {
		double result;
		if(!inv) {
			result = oper1.applyAsDouble(operand);
		} else {
			result = oper2.applyAsDouble(operand);
		}
		
		return result;
	}
}
