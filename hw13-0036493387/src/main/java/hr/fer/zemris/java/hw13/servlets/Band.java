package hr.fer.zemris.java.hw13.servlets;

/**
 * Objects of this class hold information about a single band.
 * 
 * @author Mihael Stočko
 *
 */
public class Band {
	/**
	 * Band id
	 */
	Integer id;
	
	/**
	 * Name of the band
	 */
	String name;
	
	/**
	 * Link to a sample song
	 */
	String link;
	
	/**
	 * Number of votes
	 */
	Integer votes;
	
	/**
	 * Constructor
	 */
	public Band(Integer id, String name, String link) {
		super();
		this.id = id;
		this.name = name;
		this.link = link;
	}

	/**
	 * Getter for id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Getter for name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Getter for votes
	 */
	public Integer getVotes() {
		return votes;
	}

	/**
	 * Setter for votes
	 */
	public void setVotes(Integer votes) {
		this.votes = votes;
	}
}
