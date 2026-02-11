package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This worker takes request parameters and outputs them into an html document.
 * 
 * @author Mihael Stočko
 *
 */
public class EchoParams implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		try {
			context.write("<html><body><table border = 1>");
			
			Set<String> names = context.getParameterNames();
			for(String name : names) {
				context.write("<tr>");
				context.write("<td>");
				context.write(name);
				context.write("</td>");
				context.write("<td>");
				context.write(context.getParameter(name));
				context.write("</td>");
				context.write("</tr>");
			}
			
			context.write("</table></body></html>");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}	
}
