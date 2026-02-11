package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Objects of this class have a reference to a window and listen to its state.
 * When the window is opened, the bridge is connected to the singleton provider.
 * 
 * @author Mihael Stočko
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor.
	 * 
	 * @param provider The singleton object
	 * @param frame Window that is being listened to
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowOpened(WindowEvent arg0) {
				connect();
				super.windowOpened(arg0);
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowClosed(WindowEvent arg0) {
				disconnect();
				super.windowClosing(arg0);
			}
		});
		
	}
}
