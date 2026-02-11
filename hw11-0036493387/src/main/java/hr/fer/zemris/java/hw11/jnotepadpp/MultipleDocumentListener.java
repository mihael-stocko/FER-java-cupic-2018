package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Classes that implement this interface can listen for changes in a {@link MultipleDocumentModel}
 * 
 * @author Mihael Stočko
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * When the current document is changed, this is called.
	 * 
	 * @param previousModel Tab that has previously been focused at
	 * @param currentModel The new tab
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * 
	 * This is called when a new tab is added
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * 
	 * This is called when a tab is removed
	 */
	void documentRemoved(SingleDocumentModel model);
}
