package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * This interface gives the methods that a tabbed pane for the notepad must implement.
 * 
 * @author Mihael Stočko
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Creates a new blank document
	 * 
	 * @return The newly created document
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Gets the current document
	 * 
	 * @return the current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Opens an existing document for the given path
	 * 
	 * @param path Path of the document to be opened.
	 * @return The opened document
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the document to the location given with newPath
	 * 
	 * @param model Document to be saved
	 * @param newPath Location the document is to be saved at
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes the current document
	 * 
	 * @param model Model of the closed document
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds the given {@link MultipleDocumentListener} to the list of listeners.
	 * 
	 * @param l Listener to be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes the given {@link MultipleDocumentListener} from the list of listeners.
	 * 
	 * @param l Listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Returns the number of currently opened documents.
	 * 
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns the document for the given index.
	 * The given index must be inside bounds.
	 */
	SingleDocumentModel getDocument(int index);
}
