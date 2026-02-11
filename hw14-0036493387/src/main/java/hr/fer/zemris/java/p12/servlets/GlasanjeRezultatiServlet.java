package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet reads information about options from a database, sorts the options by the number of
 * votes, picks the winners and puts them into a new list, sets both lists as global attributes,
 * and then calls a jsp that displays the results.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		
		List<PollOption> options = DAOProvider.getDao().getPollOptionsForPoll(pollID);
		
		Collections.sort(options, new Comparator<PollOption>() {
			@Override
			public int compare(PollOption arg0, PollOption arg1) {
				return arg1.getVotesCount().compareTo(arg0.getVotesCount());
			}
		});
		
		int maxVotes = 0;
		for(PollOption o : options) {
			if(o.getVotesCount() > maxVotes) {
				maxVotes = o.getVotesCount();
			}
		}
		
		List<PollOption> winners = new LinkedList<>();
		for(PollOption o : options) {
			if(o.getVotesCount() == maxVotes) {
				winners.add(o);
			}
		}
		
		req.setAttribute("options", options);
		req.setAttribute("winners", winners);
		req.setAttribute("pollID", pollID);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
