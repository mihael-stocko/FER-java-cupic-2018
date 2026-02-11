package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An implementation of {@link SingleDocumentModel}.
 * 
 * @author Mihael Stočko
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
		
	/**
	 * A path where the document is located on the disk. Null for new documents.
	 */
	private Path path;
	
	/**
	 * Text area
	 */
	private JTextArea textArea;
	
	/**
	 * Has the documents been modified
	 */
	private boolean modified = true;
	
	/**
	 * List of listeners
	 */
	private List<SingleDocumentListener> listeners = new LinkedList<>();
	
	/**
	 * Constructor.
	 * 
	 * @param path Path to the document's location. Null for new documents.
	 * @param content Initial content
	 */
	public DefaultSingleDocumentModel(Path path, String content) {
		textArea = new JTextArea(content);
		this.path = path;
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				boolean oldStatus = modified;
				modified = true;
				if(oldStatus != modified) {
					notifyListenersStatus();
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				boolean oldStatus = modified;
				modified = true;
				if(oldStatus != modified) {
					notifyListenersStatus();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "The path cannot be set to null.");
		this.path = path;
		notifyListenersPath();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyListenersStatus();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Null cannot be added to listeners.");
		listeners.add(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Null cannot be removed from listeners.");
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners that the modified status has been changed.
	 */
	private void notifyListenersStatus() {
		for(SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}
	
	/**
	 * Notifies all listeners that the path has been changed.
	 */
	private void notifyListenersPath() {
		for(SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}
}
