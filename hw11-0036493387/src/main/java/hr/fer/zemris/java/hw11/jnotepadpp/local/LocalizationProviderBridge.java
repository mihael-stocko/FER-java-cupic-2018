package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Objects of this class act as a bridge between a singleton localization provider
 * and its listeners.
 * 
 * @author Mihael Stočko
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Localization provider
	 */
	private ILocalizationProvider provider;
	
	/**
	 * A list of listeners
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Is the bridge connected to the singleton
	 */
	private boolean connected;
	
	/**
	 * Used for listening to the singleton
	 */
	private ILocalizationListener listener = new ILocalizationListener() {
		@Override
		public void localizationChanged() {
			fire();
		}
	};
	
	/**
	 * Constructor.
	 * @param The singleton object
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		listeners = new LinkedList<>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		Objects.requireNonNull(l, "Null cannot be added to listeners.");
		listeners.add(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		Objects.requireNonNull(l, "Null cannot be removed from listeners.");
		listeners.remove(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
	
	/**
	 * Used to connect to the singleton.
	 * 
	 * @throws IllegalStateException
	 */
	public void connect() {
		if(connected) {
			throw new IllegalStateException();
		}
		
		connected = true;
		
		provider.addLocalizationListener(listener);
	}
	
	/**
	 * Used to disconnect from the singleton.
	 * 
	 * @throws IllegalStateException
	 */
	public void disconnect() {
		if(!connected) {
			throw new IllegalStateException();
		}
		
		connected = false;
		
		provider.removeLocalizationListener(listener);
	}
}
