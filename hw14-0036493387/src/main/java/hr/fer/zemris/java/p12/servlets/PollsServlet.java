package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * This servlet reads a list of available polls from a database and sets the list as a global
 * attribute. It then calls polls.jsp which lists the polls on the screen.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> pollsAll = DAOProvider.getDao().getPolls();
		
		List<Poll> polls = new ArrayList<>();
		for(Poll poll : pollsAll) {
			polls.add(DAOProvider.getDao().getPoll(poll.getId()));
		}
		
		req.setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
	}
}
