package hr.fer.zemris.java.custom.collections;

/**
 * An exception used by ObjectStack class.
 * 
 * @author Mihael Stočko
 *
 */

public class EmptyStackException extends RuntimeException {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Constructor that accepts a message.
	 * @param message Exception description.
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
