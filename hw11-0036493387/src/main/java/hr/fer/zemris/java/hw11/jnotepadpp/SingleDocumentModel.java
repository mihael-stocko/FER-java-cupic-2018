package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * This interface prescribes methods for a single document.
 * 
 * @author Mihael Stočko
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Returns the text component of the document.
	 */
	JTextArea getTextComponent();
	
	/**
	 * Returns a file path to the document.
	 */
	Path getFilePath();
	
	/**
	 * Setter for the file path
	 */
	void setFilePath(Path path);
	
	/**
	 * If the document has been modified, returns <code>true</code>.
	 */
	boolean isModified();
	
	/**
	 * Setter for the modified flag.
	 * @param modified A new state
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds the given {@link SingleDocumentListener} to the list of listeners.
	 * 
	 * @param l Listener to be added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes the given {@link SingleDocumentListener} from the list of listeners.
	 * 
	 * @param l Listener to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
