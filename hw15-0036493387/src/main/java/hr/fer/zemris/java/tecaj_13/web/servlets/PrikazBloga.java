package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet shows the contents of a single entry given by a parameter and
 * offers options for adding comments and, for users who are logged in, editing the entry.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/prikazi")
public class PrikazBloga extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id")));
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + 
				entry.getCreator().getNick() + "/" + entry.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
		req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
	}
	
	/**
	 * Auxiliary method that both doGet and doPost delegate their work to.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sID = req.getParameter("id");
		Long id = null;
		try {
			id = Long.valueOf(sID);
		} catch(Exception ignorable) {
		}
		if(id!=null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
			if(blogEntry!=null) {
				req.setAttribute("blogEntry", blogEntry);
			}
		}
	}
}