package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * This class contains some useful methods for working with text documents.
 * 
 * @author Mihael Stočko
 *
 */
public class Util {

	/**
	 * Calculates statistics for the given document.
	 * 
	 * @param doc A text document
	 * @return DocumentStats object
	 */
	public static DocumentStats calculateDocumentStats(Document doc) {
		String text = "";
		
		try {
			text = doc.getText(0, doc.getLength());
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
		
		DocumentStats stats = new DocumentStats();
		
		if(!text.equals("")) {
			stats.incrementLines();
			
			char[] textChars = text.toCharArray();
			for(char c : textChars) {
				stats.incrementCharacters();
				if(!(c == ' ' || c == '\t' || c == '\n')) {
					stats.incrementNonBlankCharacters();
				}
				if(c == '\n') {
					stats.incrementLines();
				}
			}
		}
		
		return stats;
	}
	
	/**
	 * For the given document and position determines the line the given caret position is referring to.
	 * 
	 * @param doc A text document
	 * @param position Position of the caret
	 * @return Number of the line
	 */
	public static int getLineNumber(Document doc, int position) {
		if(doc.getLength() == 0) {
			return 0;
		}
		
		if(position >= doc.getLength()) {
			throw new IllegalArgumentException("The given position is too large, was " + position + ".");
		}
		
		char[] chars = null;
		try {
			chars = doc.getText(0, doc.getLength()-1).toCharArray();
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
		
		int line = 0;
		for(int i = 0; i < position; i++) {
			if(chars[i] == '\n') {
				line++;
			}
		}
		
		return line;
	}
}
