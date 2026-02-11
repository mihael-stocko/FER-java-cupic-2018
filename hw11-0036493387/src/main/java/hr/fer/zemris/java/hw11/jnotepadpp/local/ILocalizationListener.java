package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This interface should be implemented by classes that want to listen for changes
 * in a localization provider.
 * 
 * @author Mihael Stočko
 *
 */
public interface ILocalizationListener {
	
	/**
	 * This is called when a change of localization language occurs.
	 */
	public void localizationChanged();
}
