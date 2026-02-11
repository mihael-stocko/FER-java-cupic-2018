package hr.fer.zemris.java.gui.calc;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * An implementation of the CalcModel interface.
 * 
 * @author Mihael Stočko
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * List of listeners that observe this model.
	 */
	List<CalcValueListener> listeners = new LinkedList<>();
	
	/**
	 * Current value of the model.
	 */
	String value = null;
	
	/**
	 * Active operand
	 */
	double activeOperand;
	
	/**
	 * Is the activeOperand set
	 */
	boolean activeOperandSet = false;
	
	/**
	 * Binary operation to be applied next
	 */
	DoubleBinaryOperator pendingOperation = null;
	
	/**
	 * Adds a listener to the list of listeners. Cannot be null.
	 * 
	 * @throws NullPointerException
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Null cannot be added to the list of listeners.");
		
		listeners.add(l);
	}
	
	/**
	 * Removed the given listener from the list of listeners. Cannot be null.
	 * 
	 * @throws NullPointerException
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Null cannot be removed from the list of listeners.");
		
		listeners.remove(l);
	}
	
	/**
	 * Returns a string representation of the current value.
	 * If the value is null, returns "0".
	 */
	@Override
	public String toString() {
		if(value == null) {
			return "0";
		}
		
		return value;
	}
	
	/**
	 * Appends the given digit to the value. The given integer must be between 0 and 9.
	 * 
	 * @param digit Digit to be inserted
	 * @throws IllegalArgumentException
	 */
	@Override
	public void insertDigit(int digit) {
		if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException("The given argument is not a single digit.");
		}
		
		if(value != null && ((Double.MAX_VALUE - digit)/10 < getValue())) {
			return;
		}
		
		if(value == null) {
			value = "";
		}
		
		if(value.equals("0") && digit == 0) {
			return;
		}
		
		if(value.equals("0")) {
			value = Integer.toString(digit);
			notifyListeners();
			return;
		} 

		value += digit;
		notifyListeners();
	}
	
	/**
	 * Returns the current value as double. If the value is <code>null</code>, returns 0.
	 */
	@Override
	public double getValue() {
		if(value == null) {
			return 0;
		}
		
		return Double.parseDouble(value);	
	}
	
	/**
	 * Sets the current value to the value given.
	 * Cannot be NaN or infinity.
	 * 
	 * @param value Value that the current value will be set to.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setValue(double value) {
		if(value == Double.NaN || value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY) {
			throw new IllegalArgumentException("The value cannot be set to NaN or infinity.");
		}
		
		String s = Double.toString(value);
		if(s.endsWith(".0")) {
			s = s.substring(0, s.length()-2);
		}
		this.value = s;
		notifyListeners();
	}
	
	/**
	 * Swaps the sign of the current value. If the value is 0, it does nothing.
	 */
	@Override
	public void swapSign() {
		if(value != null) {
			if(value.startsWith("-")) {
				value = value.substring(1);
			} else {
				value = '-' + value;
			}
			notifyListeners();
		}
	}
	
	/**
	 * Inserts a decimal point into the current value of the model.
	 */
	@Override
	public void insertDecimalPoint() {
		if(value == null) {
			insertDigit(0);
		}
		
		if(!value.contains(".")) {
			value += '.';
			notifyListeners();
		}
	}
	
	/**
	 * Getter for active operand.
	 * 
	 * @throws IllegalStateException if the operand is not set
	 */
	@Override
	public double getActiveOperand() {
		if(!activeOperandSet) {
			throw new IllegalStateException("The active operand is not set.");
		}
		
 		return activeOperand;
	}
	
	/**
	 * Checks if the active operand is set.
	 * 
	 * @return <code>true</code> if the active operand is set, <code>false</code> otherwise.
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}
	
	/**
	 * Sets the active operand to the one provided. 
	 * Cannot be NaN or infinity.
	 * 
	 * @param activeOperand Value that the active operand will be set to.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		if(activeOperand == Double.NaN || activeOperand == Double.POSITIVE_INFINITY || 
				activeOperand == Double.NEGATIVE_INFINITY) {
			throw new IllegalArgumentException("The value cannot be set to NaN or infinity.");
		}
		
		activeOperandSet = true;
		this.activeOperand = activeOperand;
	}
	
	/**
	 * Sets the current value to 0.
	 */
	@Override
	public void clear() {
		value = "0";
		notifyListeners();
	}
	
	/**
	 * Sets the current value to 0. Clears the active operand and the pending operation.
	 */
	@Override
	public void clearAll() {
		clear();
		pendingOperation = null;
		activeOperandSet = false;
	}
	
	/**
	 * Clears the active operand.
	 */
	@Override
	public void clearActiveOperand() {
		activeOperandSet = false;
	}
	
	/**
	 * Getter for pending binary operation.
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}
	
	/**
	 * Sets the pending binary operation to the one given.
	 * If there was already a pending operation, it is first executed, and its result
	 * is stored as active operand.
	 * 
	 * @throws NullPointerException
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		Objects.requireNonNull(op, "Null cannot be set as a pending operation.");
		
		if(activeOperandSet) {
			Double r = pendingOperation.applyAsDouble(activeOperand, getValue());
			if(!r.equals(Double.NaN)) {
				setActiveOperand(r);
			} else {
				throw new IllegalArgumentException("Dividing by zero.");
			}
			pendingOperation = op;
			value = null;
		} else {
			setActiveOperand(getValue());
			pendingOperation = op;
			value = null;
		}
	}
	
	/**
	 * Notifies all listeners that a change has occurred.
	 */
	private void notifyListeners() {
		for(CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
	
	/**
	 * This is called when the button = is pressed.
	 * If the active operand is set, the pending operation is applied on it and the current value.
	 */
	public void compute() {
		if(isActiveOperandSet()) {
			Double r = pendingOperation.applyAsDouble(activeOperand, getValue());
			if(!r.equals(Double.NaN)) {
				activeOperandSet = false;
				setValue(r);
			} else {
				throw new IllegalArgumentException("Dividing by zero.");
			}
		}
	}
	
	/**
	 * Executes the given unary operation. Cannot be null.
	 * The result is stored to the current value of the model.
	 *  
	 * @param op Operation to be executed.
	 * @throws NullPointerException
	 */
	public void unaryOperation(DoubleUnaryOperator op) {
		Objects.requireNonNull(op, "Null is not a valid unary operation.");
		
		Double result = op.applyAsDouble(getValue());
		if(result != Double.NaN) {
			setValue(result);
		}
	}
}
