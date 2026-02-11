package hr.fer.zemris.java.gui.layouts;

/**
 * This exception can be cast by CalcLayout
 * 
 * @author Mihael Stočko
 *
 */
public class CalcLayoutException extends RuntimeException {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}
}
