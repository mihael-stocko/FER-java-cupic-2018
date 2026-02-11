package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element of type integer.
 * 
 * @author Mihael Stočko
 *
 */
public class ElementConstantInteger extends Element {
	
	/**
	 * Value of the integer.
	 */
	private int value;
	
	/**
	 * Constructor. Accepts the value of the integer.
	 * 
	 * @param value Value. Cannot be null.
	 * @throws NullPointerException
	 */
	public ElementConstantInteger(int name) {
		this.value = name;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return Value of the integer.
	 */
	public int getValue() {
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
