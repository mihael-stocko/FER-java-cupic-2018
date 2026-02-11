package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element of type String.
 * 
 * @author Mihael Stočko
 *
 */
public class ElementString extends Element {
	
	/**
	 * Value of the element.
	 */
	private String value;
	
	/**
	 * Constructor. Accepts a string.
	 * 
	 * @param value Value of the string. Cannot be null.
	 * @throws NullPointerException
	 */
	public ElementString(String value) {
		if(value == null) {
			throw new NullPointerException("null is not accepted as the argument.");
		}
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return Value of the string.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns the element as a string.
	 * @return String
	 */
	@Override
	public String asText() {
		return value;
	}
}
