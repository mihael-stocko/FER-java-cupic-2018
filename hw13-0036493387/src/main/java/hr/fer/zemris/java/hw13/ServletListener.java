package hr.fer.zemris.java.hw13;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This is a servlet listener that gets triggered when the application is started.
 * It saves the current time that can later be used to determine how long the application
 * has been up.
 * 
 * @author Mihael Stočko
 *
 */

@WebListener
public class ServletListener implements ServletContextListener {

	/**
	 * This is called when the application is closed. Here implemented to do nothing.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/**
	 * This is called when the application is started. Saves the current time to
	 * an attribute.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}
}
