package hr.fer.zemris.java.p12.model;

/**
 * This class model a single poll.
 * 
 * @author Mihael Stočko
 *
 */
public class Poll {

	/**
	 * Poll ID
	 */
	private long id;
	
	/**
	 * Poll title
	 */
	private String title;
	
	/**
	 * Message that will be displayed when the user selects this poll
	 */
	private String message;
	
	/**
	 * Default constructor
	 */
	public Poll() {
		super();
	}

	/**
	 * Constructor
	 */
	public Poll(long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Getter for id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Setter for id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Getter for title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Setter for message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
