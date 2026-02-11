package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element of type function.
 * 
 * @author Mihael Stočko
 *
 */
public class ElementFunction extends Element {
	
	/**
	 * Function name.
	 */
	private String name;
	
	/**
	 * Constructor. Accepts the name of the function.
	 * 
	 * @param name Name. Cannot be null.
	 * @throws NullPointerException
	 */
	public ElementFunction(String name) {
		if(name == null) {
			throw new NullPointerException("null is not accepted as the argument.");
		}
		this.name = name;
	}
	
	/**
	 * Getter for name.
	 * 
	 * @return Name of the function.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the element as a string.
	 * @return Name of the element
	 */
	@Override
	public String asText() {
		return name;
	}
}
