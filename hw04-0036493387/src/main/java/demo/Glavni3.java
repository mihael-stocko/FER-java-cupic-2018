package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This program creates a window that provides the user with a possibility to load a
 * configuration for the LSystem from a file and specify the wanted depth.
 * 
 * @author Mihael Stočko
 *
 */
public class Glavni3 {
	
	/**
	 * Main method. Arguments not used.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
