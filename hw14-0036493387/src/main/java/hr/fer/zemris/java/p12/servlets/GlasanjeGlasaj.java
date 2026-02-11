package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet increments the number of votes for the poll option given by the optionID
 * paramter by one. It dispatches the request to the servlet glasanje-rezultati with the
 * pollID as a parameter.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasaj extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("optionID"));
		
		DAOProvider.getDao().incrementOptionVotes(id);
		PollOption option = DAOProvider.getDao().getPollOptionForID(id);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/glasanje-rezultati?pollID=" 
				+ option.getPollID());
	}
}
