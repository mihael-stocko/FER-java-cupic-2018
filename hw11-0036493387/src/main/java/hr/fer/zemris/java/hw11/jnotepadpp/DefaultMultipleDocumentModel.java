package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * An implementation of {@link MultipleDocumentModel}
 * 
 * @author Mihael Stočko
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	public static final long serialVersionUID = 1L;
	
	/**
	 * List of all opened documents.
	 */
	private List<SingleDocumentModel> documents = new LinkedList<>();
	
	/**
	 * Current document
	 */
	private SingleDocumentModel currentDocument;
	
	/**
	 * List of listeners
	 */
	private List<MultipleDocumentListener> listeners = new LinkedList<>();
	
	/**
	 * Constructor.
	 */
	public DefaultMultipleDocumentModel() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		removeTabAt(documents.indexOf(model));
		documents.remove(model);
		notifyRemoved(model);
		
		if(getNumberOfDocuments() == 0) {
			createNewDocument();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = new DefaultSingleDocumentModel(null, "");
		documents.add(model);
		currentDocument = model;
		model.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if(model.isModified()) {
					setIconAt(documents.indexOf(model), JNotepadPP.redFloppy);
				} else {
					setIconAt(documents.indexOf(model), JNotepadPP.blueFloppy);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {}
		});
		notifyAdded(model);
		
		addTab("New document", new JScrollPane(model.getTextComponent()));
		setIconAt(documents.indexOf(model), JNotepadPP.redFloppy);
		setSelectedIndex(documents.indexOf(model));
		
		return model;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		if(index < 0 || index > getNumberOfDocuments()-1) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + (getNumberOfDocuments()-1) + 
					", was " + index + ".");
		}
		
		return documents.get(index);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path must not be null.");
		
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch(IOException ex) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, 
					"Error while reading the file " + path.getFileName(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String text = new String(data, StandardCharsets.UTF_8);
		
		SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
		model.setModified(false);
		boolean found = false;
		SingleDocumentModel oldModel = null;
		for(SingleDocumentModel doc : documents) {
			if(model.getFilePath().equals(doc.getFilePath())) {
				found = true;
				oldModel = doc;
				break;
			}
		}
		if(!found) {
			documents.add(model);
			notifyAdded(model);
			addTab(path.getFileName().toString(), new JScrollPane(model.getTextComponent()));
			oldModel = model;
			oldModel.addSingleDocumentListener(new SingleDocumentListener() {
				
				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					if(model.isModified()) {
						setIconAt(documents.indexOf(model), JNotepadPP.redFloppy);
					} else {
						setIconAt(documents.indexOf(model), JNotepadPP.blueFloppy);
					}
				}
				
				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {}
			});
		}
		
		currentDocument = oldModel;
		setSelectedIndex(documents.indexOf(oldModel));
		
		setIconAt(documents.indexOf(oldModel), JNotepadPP.blueFloppy);
		setToolTipTextAt(documents.indexOf(oldModel), oldModel.getFilePath().toString());
		
		return oldModel;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		for(SingleDocumentModel doc : documents) {
			if(doc.getFilePath() != null && doc.getFilePath().equals(newPath) && doc != model) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, 
						"The given path already points to an opened file.",
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		if(newPath == null) {
			newPath = model.getFilePath();
		}
		
		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, data);
		} catch(IOException ex) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, 
					"Error while writing", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, 
				"The file has been saved.", 
				"Information", 
				JOptionPane.INFORMATION_MESSAGE);
		model.setModified(false);
		setIconAt(documents.indexOf(model), JNotepadPP.blueFloppy);
		model.setFilePath(newPath);
		setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
		setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Null cannot be added to listeners.");
		listeners.add(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Null cannot be removed from listeners.");
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners that the current document has been changed.
	 * 
	 * @param Previous document
	 * @param Current document
	 */
	private void notifyCurrentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		for(MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(previousModel, currentModel);
		}
	}
	
	/**
	 * Notifies all listeners that a document has been added.
	 * 
	 * @param Newly added document
	 */
	private void notifyAdded(SingleDocumentModel model) {
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(model);
		}
	}
	
	/**
	 * Notifies all listeners that a document has been removed.
	 * 
	 * @param Removed document
	 */
	private void notifyRemoved(SingleDocumentModel model) {
		for(MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new Iterator<SingleDocumentModel>() {
			
			int i = -1;
			
			@Override
			public SingleDocumentModel next() {
				i++;
				return getDocument(i);
			}
			
			@Override
			public boolean hasNext() {
				return i < getNumberOfDocuments()-1;
			}
		};
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectedIndex(int arg0) {
		notifyCurrentChanged(currentDocument, documents.get(arg0));
		currentDocument = documents.get(arg0);
		super.setSelectedIndex(arg0);
	}
}
