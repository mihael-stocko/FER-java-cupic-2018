package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This wrapper contains one object and can perform 4 arithmetic operations on it,
 * assuming the other argument is provided. It can also compare itself with another object of the same type.
 * 
 * @author Mihael Stočko
 *
 */
public class ValueWrapper {
	
	/**
	 * Wrapped object
	 */
	private Object value;
	
	/**
	 * Constructor. Accepts the object that is to be wrapped. Can be null.
	 * @param value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Getter for the wrapped object.
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for the wrapped object.
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Adds the given object to the wrapped value. Argument can be String, Integer, Double or null, 
	 * which is treated as 0.
	 * 
	 * @param incValue
	 */
	public void add(Object incValue) {
		value = arithmeticOperation(incValue, ArithmeticOperations.SUMMATION);
	}
	
	/**
	 * Subtracts the given object from the wrapped value. Argument can be String, Integer, Double or 
	 * null, which is treated as 0.
	 * 
	 * @param incValue
	 */
	public void subtract(Object decValue) {
		value = arithmeticOperation(decValue, ArithmeticOperations.SUBTRACTION);
	}
	
	/**
	 * Multiplies the wrapped object with the given argument. Argument can be String, Integer, Double or 
	 * null, which is treated as 0.
	 * 
	 * @param incValue
	 */
	public void multiply(Object mulValue) {
		value = arithmeticOperation(mulValue, ArithmeticOperations.MULTIPLICATION);
	}
	
	/**
	 * Divides the wrapped object with the given argument. Argument can be String, Integer, Double or 
	 * null, which is treated as 0.
	 * 
	 * @param incValue
	 */
	public void divide(Object divValue) {
		value = arithmeticOperation(divValue, ArithmeticOperations.DIVISION);
	}
	
	/**
	 * Internally used method which performs arithmetic operations on the given arguments.
	 * 
	 * @param incValue First operand
	 * @param operation Second operand
	 * @return Operation
	 */
	private Object arithmeticOperation(Object incValue, ArithmeticOperations operation) {
		Object operand1;
		Object operand2;
		
		try {
			operand1 = castIntoNumber(value);
			operand2 = castIntoNumber(incValue);
		} catch(RuntimeException e) {
			throw e;
		}
		
		Object result = null;
		
		if(operand1 instanceof Double || operand2 instanceof Double) {
			Double oper1, oper2;
			if(operand1 instanceof Integer) {
				oper1 = Double.valueOf(((Integer)operand1).intValue());
			} else {
				oper1 = (Double)operand1;
			}
			
			if(operand2 instanceof Integer) {
				oper2 = Double.valueOf(((Integer)operand2).intValue());
			} else {
				oper2 = (Double)operand2;
			}
			
			result = calculateDouble(oper1, oper2, operation);
		} else {
			result = calculateInteger((Integer)operand1, (Integer)operand2, operation);
		}
		
		return result;
	}
	
	/**
	 * Casts the given object into either Integer or Double.
	 * 
	 * @param obj Object to be cast.
	 * @return
	 * @throws RuntimeException
	 */
	private Object castIntoNumber(Object obj) {
		if(obj == null) {
			return 0;
		}
		
		if(obj instanceof Integer || obj instanceof Double) {
			return obj;
		}
		
		if(obj instanceof String) {
			try {
				return Integer.parseInt((String)obj);
			} catch(Exception ignorable) {
				
			}
			
			try {
				return Double.parseDouble((String)obj);
			} catch(Exception ignorable) {
				
			}
		}
		
		throw new RuntimeException("The given object cannot be converted to a number.");
	}
	
	/**
	 * Calculates the result of the given operation on the two given Integers.
	 * 
	 * @param arg1 First operand
	 * @param arg2 Second operand 
	 * @param operation Operation
	 * @return result
	 * @throws IllegalArgumentException
	 */
	private Integer calculateInteger(Integer arg1, Integer arg2, ArithmeticOperations operation) {
		Integer result = null;
		
		switch(operation) {
		case SUMMATION:
			result = arg1 + arg2;
			break;
		case SUBTRACTION:
			result = arg1 - arg2;
			break;
		case MULTIPLICATION:
			result = arg1 * arg2;
			break;
		case DIVISION:
			if(arg2 == 0) {
				throw new IllegalArgumentException("Cannot divide by zero.");
			}
			result = arg1 / arg2;
		}
		
		return result;
	}
	
	/**
	 * Calculates the result of the given operation on the two given Doubles.
	 * 
	 * @param arg1 First operand
	 * @param arg2 Second operand 
	 * @param operation Operation
	 * @return result
	 * @throws IllegalArgumentException
	 */
	private Double calculateDouble(Double arg1, Double arg2, ArithmeticOperations operation) {
		Double result = null;
		
		switch(operation) {
		case SUMMATION:
			result = arg1 + arg2;
			break;
		case SUBTRACTION:
			result = arg1 - arg2;
			break;
		case MULTIPLICATION:
			result = arg1 * arg2;
			break;
		case DIVISION:
			if(arg2 == 0) {
				throw new IllegalArgumentException("Cannot divide by zero.");
			}
			result = arg1 / arg2;
		}
		
		return result;
	}
	
	/**
	 * Compares the given object with the wrapped object.
	 * 
	 * @param withValue
	 * @return <code>0</code> if the objects are equal, <code>-1</code> if the wrapped object is
	 * smaller than the given object, <code>1</code> otherwise.
	 */
	public int numCompare(Object withValue) {
		Object operand1;
		Object operand2;
		
		if(withValue == null && value == null) {
			return 0;
		}
		
		try {
			operand1 = castIntoNumber(value);
			operand2 = castIntoNumber(withValue);
		} catch(RuntimeException e) {
			throw e;
		}

		if(operand1 instanceof Integer) {
			operand1 = Double.valueOf(((Integer)operand1).intValue());
		}
		
		if(operand2 instanceof Integer) {
			operand2 = Double.valueOf(((Integer)operand2).intValue());
		}
		
		return ((Double)operand1).compareTo((Double)operand2);
	}
}
