package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

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
	private static DAO dao = new SQLDAO();
		
	/**
	 * Getter for the DAO object.
	 * 
	 * @return object which encapsulates access to persistence layer
	 */
	public static DAO getDao() {
		return dao;
	}
	
}