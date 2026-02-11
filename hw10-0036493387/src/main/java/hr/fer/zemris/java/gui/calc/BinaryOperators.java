package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * This class contains a DoubleBinaryOperator object for every binary operation supported by
 * the calculator.
 * 
 * @author Mihael Stočko
 *
 */
public class BinaryOperators {

	/**
	 * Addition
	 */
	public static final DoubleBinaryOperator ADD = (operand1, operand2) -> operand1 + operand2;
	
	/**
	 * Subtraction
	 */
	public static final DoubleBinaryOperator SUB = (operand1, operand2) -> operand1 - operand2;
	
	/**
	 * Multiplication
	 */
	public static final DoubleBinaryOperator MUL = (operand1, operand2) -> operand1 * operand2;
	
	/**
	 * Division. Returns NaN if the second operand is 0.
	 */
	public static final DoubleBinaryOperator DIV = (operand1, operand2) -> {
		if(operand2 == 0) {
			return Double.NaN;
		}
		
		return operand1 / operand2;
	};
	
	/**
	 * operand1^operand2
	 */
	public static final DoubleBinaryOperator POW = (operand1, operand2) -> Math.pow(operand1, operand2);
	
	/**
	 * operand2-nth root from operand1
	 */
	public static final DoubleBinaryOperator ROOT = (operand1, operand2) -> Math.pow(operand1, 1/operand2);
}
