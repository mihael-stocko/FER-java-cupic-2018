package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class represents an action that, when executed, copies the selected part of the text
 * and saves it to the clipboard.
 * 
 * @author Mihael Stočko
 *
 */
public class CopyAction extends AbstractAction {

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
	 * Clipboard
	 */
	Clipboard clipboard;
	
	/**
	 * Constructor. Registers a localization listener on the localization provider.
	 * 
	 * @param Key for getting localized strings
	 * @param Localization provider
	 * @param Multiple document model
	 * @param Clipboard
	 */
	public CopyAction(String key, ILocalizationProvider lp, MultipleDocumentModel model, Clipboard clipboard) {
		Objects.requireNonNull(model, "The model cannot be null.");
		Objects.requireNonNull(model, "The clipboard cannot be null.");
		this.model = model;
		this.clipboard = clipboard;
		
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
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();
		
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		if(len == 0) return;
		int offset = Math.min(
				textArea.getCaret().getDot(), 
				textArea.getCaret().getMark());
		try {
			clipboard.setText(doc.getText(offset, len));
		} catch(BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Updates strings to localized values.
	 */
	private void update() {
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "_description"));
	}
}