package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element of type variable.
 * 
 * @author Mihael Stočko
 *
 */
public class ElementVariable extends Element {
	
	/**
	 * Name of the variable
	 */
	private String name;
	
	/**
	 * Constructor. Accepts the name of the element.
	 * 
	 * @param name Name. Cannot be null.
	 * @throws NullPointerException
	 */
	public ElementVariable(String name) {
		if(name == null) {
			throw new NullPointerException("null is not accepted as the argument.");
		}
		this.name = name;
	}
	
	/**
	 * Getter for name.
	 * 
	 * @return Name of the element
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
