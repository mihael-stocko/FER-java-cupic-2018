package hr.fer.zemris.java.hw07.shell;

/**
 * A custom exception that can be thrown by the classes that implement Environment.
 * 
 * @author Mihael Stočko
 *
 */
public class ShellIOException extends Exception {

	public static final long serialVersionUID = 1L;
	
	public ShellIOException() {
		super();
	}
	
	public ShellIOException(String message) {
		super(message);
	}
	
	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
