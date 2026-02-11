package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.Util;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class represents an action that, when executed, retains only the first occurrence
 * from every group of same lines in the selected part of the text. If only a part of a
 * row is selected, a whole row is taken into account.
 * 
 * @author Mihael Stočko
 *
 */
public class Unique extends AbstractAction {

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
	public Unique(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
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
		int markPos = Util.getLineNumber(doc, textArea.getCaret().getDot());
		int dotPos = Util.getLineNumber(doc, textArea.getCaret().getMark());
		if(dotPos == markPos) {
			return;
		}
		
		String[] lines = textArea.getText().split("\n");
		int startLine = Math.min(markPos, dotPos);
		int endLine = Math.max(markPos, dotPos);
		
		int startPos = 0;
		for(int i = 0; i < startLine; i++) {
			startPos += lines[i].length() + 1;
		}
		
		int length = 0;
		for(int i = startLine; i <= endLine; i++) {
			length += lines[i].length() + 1;
		}
		
		try {
			doc.remove(startPos, length);
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
		
		String[] linesToAnalyze = new String[endLine - startLine + 1];
		for(int i = startLine; i <= endLine; i++) {
			linesToAnalyze[i - startLine] = lines[i];
		}
		
		Set<String> linesSet = new HashSet<>();
		for(String s : linesToAnalyze) {
			linesSet.add(s);
		}
		
		StringBuilder sb = new StringBuilder();
		for(String s : linesSet) {
			sb.append(s);
			sb.append("\n");
		}
		String insert = sb.toString();
		
		try {
			doc.insertString(startPos, insert, null);
		} catch(BadLocationException e) {
			e.printStackTrace();
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