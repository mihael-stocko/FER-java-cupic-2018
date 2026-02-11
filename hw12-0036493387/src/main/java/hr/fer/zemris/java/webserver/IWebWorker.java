package hr.fer.zemris.java.webserver;

/**
 * This interface declares one method that web workers should have.
 * 
 * @author Mihael Stočko
 *
 */
public interface IWebWorker {
	/**
	 * Processes the given request. Calculates all that is needed and writes the results
	 * to the context.
	 * 
	 * @param context Request context
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
