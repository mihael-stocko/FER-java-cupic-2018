package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * A generic localizable action
 * 
 * @author Mihael Stočko
 *
 */
public class LocalizableAction extends AbstractAction {
	
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
	 * Constructor.
	 * 
	 * @param Key for getting localized strings
	 * @param Localization provider
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		
		update();
	}
	
	/**
	 * This is called when the action is executed.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
	}
	
	/**
	 * Updates strings to localized values.
	 */
	private void update() {
		putValue(NAME, lp.getString(key));
	}
}
