package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Classes that implement this interface can listen for changes in a {@link SingleDocumentModel}
 * 
 * @author Mihael Stočko
 *
 */
public interface SingleDocumentListener {
	/**
	 * This is called when a {@link SingleDocumentModel} has its modified status changed.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * This is called when a {@link SingleDocumentModel} has its path changed.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
