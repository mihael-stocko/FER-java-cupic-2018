package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet takes a color as a parameter and sets it as a session parameter.
 * 
 * @author Mihael Stočko
 *
 */
public class SetColorServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("pickedBgCol", req.getParameter("bgcolor"));
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}
