package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This worker sets the background color and then calls the home script.
 * 
 * @author Mihael Stočko
 *
 */
public class Home implements IWebWorker {

	/**
	 * BGColor persistent parameter key
	 */
	private static final String bgcolorPersist = "bgcolor";
	
	/**
	 * BGColor temporary parameter key
	 */
	private static final String bgcolorTemp = "background";
	
	/**
	 * Default color
	 */
	private static final String defaultColor = "7F7F7F";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getPersistentParameter(bgcolorPersist);
		if(bgcolor == null) {
			context.setTemporaryParameter(bgcolorTemp, defaultColor);
		} else {
			context.setTemporaryParameter(bgcolorTemp, bgcolor);
		}
		
		context.getDispatcher().dispatchRequest("private/home.smscr");
	}
}
