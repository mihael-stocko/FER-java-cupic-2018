package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This interface model a DAO object which encapsulates access to persistence layer.
 * 
 * @author Mihael Stočko
 *
 */
public interface DAO {

	/**
	 * Gets entry with the given id.
	 * 
	 * @param id entry key
	 * @return entry
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Gets the blog user with the given nick.
	 * 
	 * @param blog user nick
	 * @return blog user
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Gets entries for the user given by nick.
	 * 
	 * @param blog user nick
	 * @return user's blog entries
	 * @throws DAOException
	 */
	public List<BlogEntry> getBlogEntries(String nick) throws DAOException;
	
	/**
	 * Gets list of blog users.
	 *
	 * @return list of authors
	 * @throws DAOException
	 */
	public List<BlogUser> getAuthors() throws DAOException;
	
	/**
	 * Adds a new comment to the given entry.
	 * 
	 * @param blogEntry Blog entry
	 * @param email email of the commenter
	 * @param message message of the commenter
	 * @throws DAOException
	 */
	public void addComment(BlogEntry blogEntry, String email, String message) throws DAOException;
	
	/**
	 * Adds a new entry to the given user.
	 * 
	 * @param blogEntry Blog user
	 * @param title title of the entry
	 * @param content content of the entry
	 * @throws DAOException
	 */
	public void addEntry(BlogUser user, String title, String content) throws DAOException;
	
	/**
	 * Updates the entry given by id
	 * 
	 * @param id Id of the entry
	 * @param title New title
	 * @param content New content
	 * @throws DAOException
	 */
	public void updateEntry(Long id, String title, String content) throws DAOException;
	
	/**
	 * Adds a new blog user to the database.
	 * 
	 * @param firstName First name
	 * @param lastName Last name
	 * @param email E-mail
	 * @param nick Nick, must be unique
	 * @param password Password
	 * @throws DAOException
	 */
	public void addUser(String firstName, String lastName, String email, String nick,
			String password) throws DAOException;
}