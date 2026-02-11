package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This interface should be implemented by classes that want to provide
 * localization settings.
 * 
 * @author Mihael Stočko
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * For the given key returns a localized string.
	 * 
	 * @param key Requested key
	 * @return localized string
	 */
	public String getString(String key);
	
	/**
	 * Adds the given listener to a list of listeners
	 * 
	 * @param l listener to be added
	 */
	public void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes the given listener from a list of listeners
	 * 
	 * @param l listener to be removed
	 */
	public void removeLocalizationListener(ILocalizationListener l);
}
