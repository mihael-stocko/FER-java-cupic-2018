package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.util.List;

/**
 * This interface model a DAO object which encapsulates access to persistence layer.
 * 
 * @author Mihael Stočko
 *
 */
public interface DAO {
	
	/**
	 * Returns a list of all available polls, but reads only ids and titles from the database.
	 * 
	 * @return List of polls
	 * @throws DAOException
	 */
	public List<Poll> getPolls() throws DAOException;
	
	/**
	 * Returns a poll for the given ID.
	 * 
	 * @param id ID of the requested poll
	 * @return Poll for the given id
	 * @throws DAOException
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Returns a list of poll options for the given poll ID.
	 * 
	 * @param id ID of the poll
	 * @return List of all poll options linked to the poll given with ID.
	 * @throws DAOException
	 */
	public List<PollOption> getPollOptionsForPoll(long id) throws DAOException;
	
	/**
	 * Returns a poll option for the given ID.
	 * 
	 * @param id ID of the requested poll option
	 * @return PollOption for the given id
	 * @throws DAOException
	 */
	public PollOption getPollOptionForID(long id) throws DAOException;
	
	/**
	 * Increments the valueCount of the PollOption given by id by one.
	 * 
	 * @param id ID of the PollOption that is to be incremented.
	 * @throws DAOException
	 */
	public void incrementOptionVotes(long id) throws DAOException;
}