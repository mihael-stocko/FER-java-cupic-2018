package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * This class is used to store connections to a database inside a ThreadLocal object.
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Set a connection for the current thread (or delete the entry from the map if the argument is null).
	 * 
	 * @param con Connection to a database
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Get the connection the current thread may use.
	 * 
	 * @return Connection to a database
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}