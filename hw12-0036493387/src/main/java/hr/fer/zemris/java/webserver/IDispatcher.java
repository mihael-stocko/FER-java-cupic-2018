package hr.fer.zemris.java.webserver;

/**
 * Classes that implement this interface must provide a method that dispatches the file
 * given by path to a request context.
 * 
 * @author Mihael Stočko
 *
 */
public interface IDispatcher {
	/**
	 * Dispatches the given file to a request context.
	 * 
	 * @param urlPath File to dispatch
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
