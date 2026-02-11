package hr.fer.zemris.java.hw17;

import java.util.LinkedList;
import java.util.List;

/**
 * This is a representation of a single image.
 * 
 * @author Mihael Stočko
 *
 */
public class Image {
	
	/**
	 * Image title
	 */
	String title;
	
	/**
	 * Image description
	 */
	String description;
	
	/**
	 * List of tags
	 */
	List<String> tags = new LinkedList<>();
	
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
	 * Getter for description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter for description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter for tags
	 */
	public List<String> getTags() {
		return tags;
	}
	
	/**
	 * Setter for tags
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
