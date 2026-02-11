package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This worker changes the background color to the one provided with a parameter from the
 * context. It outputs the message saying whether the color has been changed, and writes a link
 * to the home page.
 * 
 * @author Mihael Stočko
 *
 */
public class BgColorWorker implements IWebWorker {
	
	/**
	 * BGColor parameter key
	 */
	private static final String bgcolorParam = "bgcolor";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter(bgcolorParam).toUpperCase();
		boolean valid = true;
		if(color.length() == 6) {
			for(int i = 0; i < 6; i++) {
				char c = color.charAt(i);
				if(!Character.isDigit(c) && 
						c != 'A' && c != 'B' && c != 'C' && c != 'D' && c != 'E' && c != 'F') {
					valid = false;
					break;
				}
			}
		} else {
			valid = false;
		}
		
		if(valid) {
			context.setPersistentParameter(bgcolorParam, color);
		}
		
		context.setMimeType("text/html");
		context.write("<html><head><title>BGColor Info</title></head><body>The background color was ");
		if(!valid) {
			context.write("not ");
		}
		context.write("changed.<p><a href=\"\\index2.html\">Back to index</a>");
		context.write("</body></html>");
	}
}