package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class represents an action that, when executed, saves the current document
 * to the disk. The user is always asked for the path to a desired location. If the file
 * already exists, the user will be warned.
 * 
 * @author Mihael Stočko
 *
 */
public class SaveAsAction extends AbstractAction {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Key for getting localized strings
	 */
	private String key;
	
	/**
	 * Localization provider used for getting strings in an arbitrary language.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Multiple document model - holds tabbed single document models
	 */
	MultipleDocumentModel model;
	
	/**
	 * Main frame
	 */
	JNotepadPP frame;
	
	/**
	 * Constructor. Registers a localization listener on the localization provider.
	 * 
	 * @param Key for getting localized strings
	 * @param Localization provider
	 * @param Multiple document model
	 * @param Main frame
	 */
	public SaveAsAction(String key, ILocalizationProvider lp, JNotepadPP frame, MultipleDocumentModel model) {
		Objects.requireNonNull(model, "The model cannot be null.");
		Objects.requireNonNull(frame, "The frame cannot be null.");
		this.model = model;
		this.frame = frame;
		
		Objects.requireNonNull(key, "Key cannot be null.");
		Objects.requireNonNull(lp, "Localization provider cannot be null.");
		this.key = key;
		this.lp = lp;
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				update();
			}
		});
		
		update();
	}
	
	/**
	 * This is called when the action is executed.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save file");
		if(fc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		if(filePath.toFile().exists()) {
			Object[] options = {"Yes",
            		"No"};
			int n = JOptionPane.showOptionDialog((Component)frame.getContentPane(),
					"The file already exists. Overwrite?",
					"Warning",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null,
					options,
					options[1]);
			if(n == 1) {
				return;
			}
		}
		
		model.saveDocument(model.getCurrentDocument(), filePath);
	}
	
	/**
	 * Updates strings to localized values.
	 */
	private void update() {
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "_description"));
	}
}
