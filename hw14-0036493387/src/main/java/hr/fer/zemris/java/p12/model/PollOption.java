package hr.fer.zemris.java.p12.model;

/**
 * This class models a single poll option.
 * 
 * @author Mihael Stočko
 *
 */
public class PollOption {

	/**
	 *  Poll option id
	 */
	private long id;
	
	/**
	 * Poll option title
	 */
	private String optionTitle;
	
	/**
	 * Link to an example for this option
	 */
	private String optionLink;
	
	/**
	 * Number of votes
	 */
	private int votesCount;
	
	/**
	 * ID of the poll this option belongs to
	 */
	private long pollID;
	
	/**
	 * Default constructor
	 */
	public PollOption() {
		super();
	}
	
	/**
	 * Constructor
	 */
	public PollOption(long id, String optionTitle, String optionLink, int votesCount, long pollID) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
		this.pollID = pollID;
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
	 * Getter for optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for optionTitle
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for optionLink
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Getter for votesCount
	 */
	public Integer getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for votesCount
	 */
	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}

	/**
	 * Getter for pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for pollID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}
}
