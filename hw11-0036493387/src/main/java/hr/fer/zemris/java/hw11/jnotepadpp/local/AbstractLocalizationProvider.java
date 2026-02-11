package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This abstract class implements ILocalizationProvider but doesn't implement the method
 * getString(). 
 * 
 * @author Mihael Stočko
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * A list of listeners
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Constructor
	 */
	public AbstractLocalizationProvider() {
		listeners = new LinkedList<>();
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
	 * Notifies all listeners that a change has occurred.
	 */
	public void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
