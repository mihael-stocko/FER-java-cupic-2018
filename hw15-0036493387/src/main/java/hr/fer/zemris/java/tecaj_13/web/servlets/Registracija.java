package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * This servlet registers the user defined by parameters into the database.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/register")
public class Registracija extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/Registracija.jsp").forward(req, resp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String metoda = req.getParameter("metoda");
		if(!"Registriraj se".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		
		String firstName = Util.pripremi(req.getParameter("firstName"));
		String lastName = Util.pripremi(req.getParameter("lastName"));
		String email = Util.pripremi(req.getParameter("email"));
		String nick = Util.pripremi(req.getParameter("nick"));
		String password = Util.pripremi(req.getParameter("password"));
		
		if(firstName.equals("") || lastName.equals("") || email.equals("") || 
				nick.equals("") || password.equals("")) {
			req.setAttribute("error", "Sva polja su obvezna.");
			req.getRequestDispatcher("/WEB-INF/pages/Registracija.jsp").forward(req, resp);
			return;
		}
		
		if(DAOProvider.getDAO().getBlogUser(nick) != null) {
			req.setAttribute("error", "Već postoji korisnik s tim nickom.");
			req.getRequestDispatcher("/WEB-INF/pages/Registracija.jsp").forward(req, resp);
			return;
		}
		
		DAOProvider.getDAO().addUser(firstName, lastName, email, nick, password);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
