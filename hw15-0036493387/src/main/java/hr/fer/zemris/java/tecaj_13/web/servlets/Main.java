package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This servlet renders a main page that shows login form, a link for registration and
 * a list of all blog users.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/main")
public class Main extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}
	
	/**
	 * Auxiliary method that both doGet and doPost delegate their work to.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getAuthors();
		req.setAttribute("users", users);
		
		req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
	}
}
