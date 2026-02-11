package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.DocumentStats;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.Util;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class represents an action that, when executed, shows statistics for the
 * currently opened document.
 * 
 * @author Mihael Stočko
 *
 */
public class StatisticsAction extends AbstractAction {

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
	public StatisticsAction(String key, ILocalizationProvider lp, JNotepadPP frame, MultipleDocumentModel model) {
		Objects.requireNonNull(frame, "The frame cannot be null.");
		Objects.requireNonNull(model, "The model cannot be null.");
		this.frame = frame;
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
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		DocumentStats stats = Util.calculateDocumentStats(doc);
		
		JOptionPane.showMessageDialog(frame, 
				"Number of characters: " + stats.getCharacters() + "\n" + 
				"Number of non-blank characters: " + stats.getNonBlankCharacters() +  "\n" + 
				"Number of lines: " + stats.getLines(), 
				"Statistics", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Updates strings to localized values.
	 */
	private void update() {
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "_description"));
	}
}
