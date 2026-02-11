package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A representation of a text node.
 * 
 * @author Mihael Stočko
 *
 */
public class TextNode extends Node {
	
	/**
	 * String value of the node.
	 */
	private String text;
	
	/**
	 * Constructor.
	 * 
	 * @param text String to be stored inside a node.
	 * @throws NullPointerException
	 */
	public TextNode(String text) {
		super();
		if(text == null) {
			throw new NullPointerException("TextNode cannot contain a null reference.");
		}
		this.text = text;
	}
	
	/**
	 * Getter for text;
	 * 
	 * @return String value of the node.
	 */
	public String getText() {
		return text;
	}
}
