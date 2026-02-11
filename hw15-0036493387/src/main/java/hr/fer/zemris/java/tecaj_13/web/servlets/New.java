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
 * This servlet creates a new entry and saves it into the database.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/new")
public class New extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("current.user.id") == null) {
			req.getRequestDispatcher("/WEB-INF/pages/Forbidden.jsp").forward(req, resp);
			return;
		}
		
		if(!req.getSession().getAttribute("current.user.nick").equals(req.getAttribute("entryNick"))) {
			req.getRequestDispatcher("/WEB-INF/pages/Forbidden.jsp").forward(req, resp);
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/NewEntry.jsp").forward(req, resp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		String user = (String)req.getAttribute("user");
		BlogUser u = DAOProvider.getDAO().getBlogUser(user);
		
		if(!user.equals(req.getSession().getAttribute("current.user.nick"))) {
			req.getRequestDispatcher("/WEB-INF/pages/Forbidden.jsp").forward(req, resp);
			return;
		}
		
		if(title.equals("") || content.equals("")) {
			req.setAttribute("error", "Sva polja su obvezna.");
			req.getRequestDispatcher("/WEB-INF/pages/NewEntry.jsp").forward(req, resp);
			return;
		}
		
		DAOProvider.getDAO().addEntry(u, title, content);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + u.getNick());
	}
}
