package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.Util;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This class represents an action that, when executed, sorts the lines in the selected
 * part of the text. If only a part of a row is selected, a whole row is taken into account.
 * It is specified through the constructor whether the sort will be ascending or descending.
 * 
 * @author Mihael Stočko
 *
 */
public class Sort extends AbstractAction {

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
	 * Is the sort descending
	 */
	private boolean descending;
	
	/**
	 * Multiple document model - holds tabbed single document models
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Constructor. Registers a localization listener on the localization provider.
	 * 
	 * @param Key for getting localized strings
	 * @param Localization provider
	 * @param Multiple document model
	 * @param Descending
	 */
	public Sort(String key, ILocalizationProvider lp, MultipleDocumentModel model, boolean descending) {
		Objects.requireNonNull(model, "The model cannot be null.");
		this.model = model;
		
		Objects.requireNonNull(key, "Key cannot be null.");
		Objects.requireNonNull(lp, "Localization provider cannot be null.");
		this.key = key;
		this.lp = lp;
		
		this.descending = descending;
		
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
			if((startPos + length) > doc.getLength()) {
				length--;
			}
			System.out.println(startPos + ", " + length);
			doc.remove(startPos, length);
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
		
		String[] linesToSort = new String[endLine - startLine + 1];
		for(int i = startLine; i <= endLine; i++) {
			linesToSort[i - startLine] = lines[i];
		}
		
		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator collator = Collator.getInstance(locale);
		
		List<String> linesToSortList = Arrays.asList(linesToSort);
		Collections.sort(linesToSortList, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				int r = collator.compare(s1, s2);
				return descending ? -r : r;
			}
		});
		
		StringBuilder sb = new StringBuilder();
		for(String s : linesToSortList) {
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