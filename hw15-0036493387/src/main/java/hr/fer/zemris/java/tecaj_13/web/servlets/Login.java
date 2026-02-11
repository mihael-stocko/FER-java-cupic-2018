package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This servlet logs the user defined by parameters in, if the data are valid.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = Util.pripremi(req.getParameter("nick"));
		String password = Util.pripremi(req.getParameter("password"));
		
		if(nick.equals("") || password.equals("")) {
			req.setAttribute("error", "Oba polja su obvezna.");
			req.getRequestDispatcher("/servleti/main").forward(req, resp);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if(user == null) {
			req.setAttribute("error", "Korisničko ime ne postoji.");
			req.getRequestDispatcher("/servleti/main").forward(req, resp);
			return;
		}
		
		if(!user.getPasswordHash().equals(Util.getDigest(password))) {
			req.setAttribute("error", "Korisničko ime i lozinka se ne podudaraju.");
			req.setAttribute("nick", nick);
			req.getRequestDispatcher("/servleti/main").forward(req, resp);
			return;
		}
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
