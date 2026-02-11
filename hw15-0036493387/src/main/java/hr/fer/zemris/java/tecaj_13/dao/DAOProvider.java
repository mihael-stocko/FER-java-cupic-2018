package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * A DAO singleton. The singleton object encapsulates access to persistence layer.
 * 
 * @author Mihael Stočko
 *
 */
public class DAOProvider {

	/**
	 * A single DAO object.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Getter for the DAO object.
	 * 
	 * @return object which encapsulates access to persistence layer
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}