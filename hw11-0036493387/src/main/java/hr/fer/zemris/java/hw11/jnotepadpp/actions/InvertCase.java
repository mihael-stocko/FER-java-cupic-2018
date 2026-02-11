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
 * This class represents an action that, when executed, inverts the case of the selected text.
 * 
 * @author Mihael Stočko
 *
 */
public class InvertCase extends AbstractAction {

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
	 * Constructor. Registers a localization listener on the localization provider.
	 * 
	 * @param Key for getting localized strings
	 * @param Localization provider
	 * @param Multiple document model
	 */
	public InvertCase(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
		Objects.requireNonNull(model, "The model cannot be null.");
		this.model = model;
		
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
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = 0;
		if(len != 0) {
			offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		} else {
			return;
		}
		try {
			String text = doc.getText(offset, len);
			text = changeCase(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Updates strings to localized values.
	 */
	private void update() {
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "_description"));
	}
	
	/**
	 * Changes the case of the every character in the given string.
	 * 
	 * @return A String with inverted case
	 */
	private String changeCase(String text) {
		char[] znakovi = text.toCharArray();
		for(int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if(Character.isLowerCase(c)) {
				znakovi[i] = Character.toUpperCase(c);
			} else if(Character.isUpperCase(c)) {
				znakovi[i] = Character.toLowerCase(c);
			}
		}
		return new String(znakovi);
	}
}