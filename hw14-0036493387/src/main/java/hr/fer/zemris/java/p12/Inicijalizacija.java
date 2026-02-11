package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This is a listener that is used to initialize the web application.
 * If the tables Polls and PollOptions do not exist, it creates them.
 * If there are no polls, it creates two default ones.
 * If there are no options for one or all of the default polls, it fills them
 * into the PollOptions table.
 * 
 * @author Mihael Stočko
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Title of the first default poll
	 */
	public static final String defaultPollTitle1 = "Glasanje za najbolji bend";
	
	/**
	 * Message of the first default poll
	 */
	public static final String defaultPollMessage1 = "Koji vam je od ovih bendova najdraži?";
	
	/**
	 * Title of the second default poll
	 */
	public static final String defaultPollTitle2 = "Glasanje za YuGiOh karte";
	
	/**
	 * Message of the second default poll
	 */
	public static final String defaultPollMessage2 = "Koja vam je YuGiOh karta najdraža?";
	
	/**
	 * Has the initializer created the tables?
	 */
	private boolean pollsFilled = false;
	
	/**
	 * List of default options for the first default poll
	 */
	private static List<PollOption> options1 = new LinkedList<>();
	
	/**
	 * List of default options for the second default poll
	 */
	private static List<PollOption> options2 = new LinkedList<>();
	
	/**
	 * This static block fills the default options into their respective lists.
	 */
	static {
		options1.add(new PollOption(0, "The Beatles", 
				"https://www.youtube.com/watch?v=z9ypq6_5bsg", 150, 0));
		options1.add(new PollOption(0, "The Platters", 
				"https://www.youtube.com/watch?v=H2di83WAOhU", 60, 0));
		options1.add(new PollOption(0, "The Beach Boys", 
				"https://www.youtube.com/watch?v=2s4slliAtQU", 150, 0));
		options1.add(new PollOption(0, "The Four Seasons", 
				"https://www.youtube.com/watch?v=y8yvnqHmFds", 20, 0));
		options1.add(new PollOption(0, "The Marcels", 
				"https://www.youtube.com/watch?v=qoi3TH59ZEs", 33, 0));
		options1.add(new PollOption(0, "The Everly Brothers", 
				"https://www.youtube.com/watch?v=tbU3zdAgiX8", 25, 0));
		options1.add(new PollOption(0, "The Mamas And The Papas", 
				"https://www.youtube.com/watch?v=N-aK6JnyFmk", 20, 0));
		
		options2.add(new PollOption(0, "Exodijina druga ruka", 
				"https://vignette.wikia.nocookie.net/yugioh/images/f/f5/LeftLegoftheForbiddenOne-LDK2-EN-C-1E.png/revision/latest?cb=20161007081813", 0, 0));
		options2.add(new PollOption(0, "Ono zeleno čudo", 
				"https://vignette.wikia.nocookie.net/yugioh/images/f/f8/OjamaGreen-DP2-EN-C-1E.jpg/revision/latest?cb=20061014224804", 0, 0));
		options2.add(new PollOption(0, "Tatsunootoshigo",
				"https://vignette.wikia.nocookie.net/yugioh/images/e/ee/Tatsunootoshigo-B2-JP-C.png/revision/latest?cb=20140510030529", 0, 0));
		options2.add(new PollOption(0, "Pikachu", 
				"https://52f4e29a8321344e30ae-0f55c9129972ac85d6b1f4e703468e6b.ssl.cf2.rackcdn.com/products/pictures/150095.jpg", 0, 0));
		options2.add(new PollOption(0, "Deathwing", 
				"http://pm1.narvii.com/6260/25d95b69cb38ce0c59f9f088eac65be97ff6950a_00.jpg", 0, 0));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String dbName;
		String connectionURL = null;
		
		String path = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		if(!Paths.get(path).toFile().exists()) {
			throw new IllegalStateException("The properties file does not exist.");
		}
		
		try(InputStream is = new FileInputStream(path)) {
			Properties prop = new Properties();
			prop.load(is);
			String name = prop.getProperty("name");
			String host = prop.getProperty("host");
			String port = prop.getProperty("port");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");	
			if(name == null || host == null || port == null || user == null || password == null) {
				throw new IllegalStateException("The properties file is not valid.");
			}
			
			dbName = name;
			connectionURL = "jdbc:derby://" + host + ":" + port + "/" +
					dbName + ";user=" + user + ";password=" + password;
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		Connection con = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			con = DriverManager.getConnection(connectionURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "POLLS", null);
			if(!tables.next()) {
				PreparedStatement pst = con.prepareStatement("CREATE TABLE Polls\r\n" + 
						" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
						" title VARCHAR(150) NOT NULL,\r\n" + 
						" message CLOB(2048) NOT NULL\r\n" + 
						")");
				pst.executeUpdate();
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		try {
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "POLLOPTIONS", null);
			if (!tables.next()) {
				PreparedStatement pst = con.prepareStatement("CREATE TABLE PollOptions\r\n" + 
						" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
						" optionTitle VARCHAR(100) NOT NULL,\r\n" + 
						" optionLink VARCHAR(150) NOT NULL,\r\n" + 
						" pollID BIGINT,\r\n" + 
						" votesCount BIGINT,\r\n" + 
						" FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + 
						")");
				pst.executeUpdate();
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}

		try(PreparedStatement pst = con.prepareStatement("SELECT * FROM Polls")) {
			ResultSet rset = pst.executeQuery();
			if(!rset.next()) {
				pollsFilled = true;
				try(PreparedStatement insert = con.prepareStatement(
						"INSERT INTO Polls (title, message) values (?,?)",
						Statement.RETURN_GENERATED_KEYS)) {
					insert.setString(1, defaultPollTitle1);
					insert.setString(2, defaultPollMessage1);
					insert.executeUpdate();
					insert.setString(1, defaultPollTitle2);
					insert.setString(2, defaultPollMessage2);
					insert.executeUpdate();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		if(pollsFilled) {
			long id1 = 0;
			long id2 = 0;
			
			try(PreparedStatement pst = con.prepareStatement("SELECT id FROM Polls WHERE title='"
					+ defaultPollTitle1 + "'")) {
				ResultSet rset = pst.executeQuery();
				rset.next();
				id1 = rset.getLong("id");
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
			try(PreparedStatement pst = con.prepareStatement("SELECT id FROM Polls WHERE title='"
					+ defaultPollTitle2 + "'")) {
				ResultSet rset = pst.executeQuery();
				rset.next();
				id2 = rset.getLong("id");
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
			try(PreparedStatement pst = con.prepareStatement("SELECT * FROM PollOptions WHERE pollID=" + id1)) {
				ResultSet rset = pst.executeQuery();
				if(!rset.next()) {
					try(PreparedStatement insert = con.prepareStatement(
							"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS)) {
						for(PollOption p : options1) {
							insert.setString(1, p.getOptionTitle());
							insert.setString(2, p.getOptionLink());
							insert.setLong(3, id1);
							insert.setInt(4, p.getVotesCount());
							insert.executeUpdate();
						}
					} catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		
			try(PreparedStatement pst = con.prepareStatement("SELECT * FROM PollOptions WHERE pollID=" + id2)) {
				ResultSet rset = pst.executeQuery();
				if(!rset.next()) {
					try(PreparedStatement insert = con.prepareStatement(
							"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS)) {
						for(PollOption p : options2) {
							insert.setString(1, p.getOptionTitle());
							insert.setString(2, p.getOptionLink());
							insert.setLong(3, id2);
							insert.setInt(4, p.getVotesCount());
							insert.executeUpdate();
						}
					} catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		try { 
			con.close(); 
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}