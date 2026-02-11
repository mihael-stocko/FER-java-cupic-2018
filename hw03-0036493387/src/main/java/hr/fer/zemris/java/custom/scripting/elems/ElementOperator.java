package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element of type operator.
 * 
 * @author Mihael Stočko
 *
 */
public class ElementOperator extends Element {
	
	/**
	 * Operator type.
	 */
	private String symbol;
	
	/**
	 * Constructor. Accepts the type of the element.
	 * 
	 * @param type Type. Cannot be null.
	 * @throws NullPointerException
	 */
	public ElementOperator(String symbol) {
		if(symbol == null) {
			throw new NullPointerException("null is not accepted as the argument.");
		}
		this.symbol = symbol;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return Type of the operator.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Returns the element as a string.
	 * @return Name of the element
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
