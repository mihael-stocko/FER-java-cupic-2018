package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * This servlet redirects the user to the form used for editing a single entry.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/edit")
public class Edit extends HttpServlet {

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
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id")));
		if(!req.getSession().getAttribute("current.user.nick").equals(entry.getCreator().getNick())) {
			req.getRequestDispatcher("/WEB-INF/pages/Forbidden.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("blogEntry", entry);
		req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp").forward(req, resp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		Long id = Long.parseLong(req.getParameter("id"));
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		DAOProvider.getDAO().updateEntry(id, title, content);
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
	
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + 
		entry.getCreator().getNick() + "/" + entry.getId());
	}
}
