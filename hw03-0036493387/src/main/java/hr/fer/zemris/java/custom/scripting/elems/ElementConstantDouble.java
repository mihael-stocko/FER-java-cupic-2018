package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element of type double.
 * 
 * @author Mihael Stočko
 *
 */
public class ElementConstantDouble extends Element {
	
	/**
	 * Value of the double.
	 */
	private double value;
	
	/**
	 * Constructor. Accepts the value of the double.
	 * 
	 * @param value Value. Cannot be null.
	 * @throws NullPointerException
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return Value of the double.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns the element as a string.
	 * @return Value of the element as string.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
