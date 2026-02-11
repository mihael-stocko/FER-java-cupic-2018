package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleUnaryOperator;

/**
 * This class contains a DoubleUnaryOperator object for every unary operation supported by
 * the calculator.
 * 
 * @author Mihael Stočko
 *
 */
public class UnaryOperators {

	/**
	 * Function 1/x
	 */
	public static final DoubleUnaryOperator oneOverX = operand -> {
		if(operand == 0) {
			return Double.NaN;
		}
		
		return 1/operand;
	};
	
	/**
	 * Function sin(x)
	 */
	public static final DoubleUnaryOperator sin = operand -> Math.sin(operand);
	
	/**
	 * Function asin(x)
	 */
	public static final DoubleUnaryOperator sinInv = operand -> Math.asin(operand);
	
	/**
	 * Function cos(x)
	 */
	public static final DoubleUnaryOperator cos = operand -> Math.cos(operand);
	
	/**
	 * Function acos(x)
	 */
	public static final DoubleUnaryOperator cosInv = operand -> Math.acos(operand);
	
	/**
	 * Function tan(x)
	 */
	public static final DoubleUnaryOperator tan = operand -> Math.tan(operand);
	
	/**
	 * Function atan(x)
	 */
	public static final DoubleUnaryOperator tanInv = operand -> Math.atan(operand);
	
	/**
	 * Function ctg(x)
	 */
	public static final DoubleUnaryOperator ctg = operand -> Math.pow(Math.tan(operand), -1);
	
	/**
	 * Function actg(x)
	 */
	public static final DoubleUnaryOperator ctgInv = operand -> Math.atan(Math.pow(operand, -1));
	
	/**
	 * Function log(x)
	 */
	public static final DoubleUnaryOperator log = operand -> Math.log10(operand);
	
	/**
	 * Function 10^x
	 */
	public static final DoubleUnaryOperator logInv = operand -> Math.pow(10, operand);
	
	/**
	 * Function ln(x)
	 */
	public static final DoubleUnaryOperator ln = operand -> Math.log(operand);
	
	/**
	 * Function e^x
	 */
	public static final DoubleUnaryOperator lnInv = operand -> Math.pow(Math.E, operand);
}
