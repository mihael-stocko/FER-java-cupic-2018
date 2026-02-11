package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is a singleton class that extends AbstractLocalizationProvider.
 * It implements the getString method and offers an option to change the
 * current language. The default language is English.
 * 
 * @author Mihael Stočko
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * A singeton instance
	 */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * The current language
	 */
	private String language;
	
	/**
	 * ResourceBundle object
	 */
	private ResourceBundle bundle;
	
	/**
	 * Constructor
	 */
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));
	}
	
	/**
	 * Sets the language to the one provided. Notifies all observers.
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle(bundle.getBaseBundleName(), Locale.forLanguageTag(language));
		fire();
	}
	
	/**
	 * Getter for the singleton instance
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Gets the localized string for the given key.
	 */
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Getter for language
	 */
	public String getLanguage() {
		return language;
	}
}
