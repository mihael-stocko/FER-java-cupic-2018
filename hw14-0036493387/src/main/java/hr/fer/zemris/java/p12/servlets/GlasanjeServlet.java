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
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet reads information about options from a database and sets a list of options
 * as a global attribute. It calls the jsp that shows a link for each option.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("pollID"));
		
		List<PollOption> pollOptionsAll = DAOProvider.getDao().getPollOptionsForPoll(id);
		
		List<PollOption> pollOptions = new ArrayList<>();
		for(PollOption pollOption : pollOptionsAll) {
			pollOptions.add(DAOProvider.getDao().getPollOptionForID(pollOption.getId()));
		}
		
		Poll poll = DAOProvider.getDao().getPoll(id);
		req.setAttribute("pollOptions", pollOptions);
		req.setAttribute("message", poll.getMessage());
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanje.jsp").forward(req, resp);
	}
}
