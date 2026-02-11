package hr.fer.zemris.java.hw11.jnotepadpp.actions;

/**
 * A simple clipboard
 * 
 * @author Mihael Stočko
 *
 */
public class Clipboard {

	/**
	 * Text being held in the clipboard
	 */
	String text;
	
	/**
	 * Constructor
	 */
	public Clipboard(String text) {
		this.text = text;
	}

	/**
	 * Getter for text
	 * 
	 * @return Text being held
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for text
	 */
	public void setText(String text) {
		this.text = text;
	}
}
