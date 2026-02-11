package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * This servlet is triggered by all paths starting with "servleti/author/".
 * It parses the rest of the path and determines whether a list of entries is to be
 * display, a single entry is to be displayed, or an entry is to be added or modified.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/author/*")
public class ListEntries extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		String email;
		if("true".equals(req.getParameter("loggedIn"))) {
			email = DAOProvider.getDAO().getBlogUser(req.getParameter("loggedNick")
					.substring(0, req.getParameter("loggedNick").length()-1)).getEmail();
		} else {
			email = Util.pripremi(req.getParameter("email"));
		}
		String message = Util.pripremi(req.getParameter("message"));
		
		String param = req.getPathInfo().substring(1);
		
		if(param.endsWith("/new")) {
			String user = param.substring(0, param.indexOf("/new"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			req.setAttribute("title", title);
			req.setAttribute("content", content);
			req.setAttribute("user", user);
			
			req.getRequestDispatcher("/servleti/new").forward(req, resp);
			return;
		} else if(param.endsWith("/edit")) {
			req.getRequestDispatcher("/servleti/edit").forward(req, resp);
			return;
		}
		
		Long id = 0L;
		try {
			id = Long.parseLong(param.substring(param.indexOf("/")+1));
		} catch(NumberFormatException e) {
			
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		
		DAOProvider.getDAO().addComment(entry, email, message);
		
		req.setAttribute("blogEntry", entry);
		req.getRequestDispatcher("/servleti/prikazi").forward(req, resp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().substring(1);
		
		String nick = null;
		String eid = null;
		
		String user = (String)req.getSession().getAttribute("current.user.nick");
		
		if(path.contains("/")) {
			String[] strings = path.split("/");
			nick = strings[0];
			eid = strings[1];
			
			if(nick.equals(user)) {
				req.setAttribute("editForm", true);
			}
			
			if(eid.startsWith("edit")) {
				req.getRequestDispatcher("/servleti/edit").forward(req, resp);
				return;
			} else if(eid.startsWith("new")) {
				req.setAttribute("entryNick", nick);
				req.getRequestDispatcher("/servleti/new").forward(req, resp);
				return;
			}
			
			Long id = 0L;
			try {
				id = Long.parseLong(eid);
			} catch(NumberFormatException e) {
				req.getRequestDispatcher("/WEB-INF/pages/Invalid.jsp").forward(req, resp);
				return;
			}
			
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(nick);
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			if(entries.contains(entry)) {
				req.setAttribute("blogEntry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
				return;
			} else {
				req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
				return;
			}
		} else {
			nick = path;
			
			if(nick.equals(user)) {
				req.setAttribute("addForm", true);
			}
			
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(nick);
			
			req.setAttribute("entries", entries);
			req.setAttribute("nick", nick);
			
			req.getRequestDispatcher("/WEB-INF/pages/EntryList.jsp").forward(req, resp);
		}
	}
}
