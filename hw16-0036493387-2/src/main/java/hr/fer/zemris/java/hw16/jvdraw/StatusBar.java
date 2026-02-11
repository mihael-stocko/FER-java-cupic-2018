package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;

/**
 * This is a status bar that shows the RGB components of the currently selected foreground
 * and background colors.
 * 
 * @author Mihael Stočko
 *
 */
public class StatusBar extends JPanel implements ColorChangeListener {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Foreground color provider.
	 */
	private IColorProvider provider1;
	
	/**
	 * Background color provider.
	 */
	private IColorProvider provider2;
	
	/**
	 * Label for showing the results
	 */
	private JLabel label;

	/**
	 * Consturctor.
	 */
	public StatusBar(IColorProvider provider1, IColorProvider provider2) {
		super();
		this.provider1 = provider1;
		this.provider2 = provider2;
		
		provider1.addColorChangeListener(this);
		provider2.addColorChangeListener(this);
		
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(0, 25));
		
		setForeground(Color.BLACK);
		label = new JLabel("Foreground color: (" + provider1.getCurrentColor().getRed() + ", " +
			provider1.getCurrentColor().getGreen()  + ", " + provider1.getCurrentColor().getBlue() + 
			"), background color: (" + provider2.getCurrentColor().getRed() + ", " + 
			provider2.getCurrentColor().getGreen() + ", " + provider2.getCurrentColor().getBlue() + ").");
		
		add(label);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if(source == provider1) {
			label.setText("Foreground color: (" + newColor.getRed() + ", " +
					newColor.getGreen()  + ", " + newColor.getBlue() + 
					"), background color: (" + provider2.getCurrentColor().getRed() + ", " + 
					provider2.getCurrentColor().getGreen() + ", " + provider2.getCurrentColor().getBlue() + ").");
		} else {
			label.setText("Foreground color: (" + provider1.getCurrentColor().getRed() + ", " +
					provider1.getCurrentColor().getGreen()  + ", " + provider1.getCurrentColor().getBlue() + 
					"), background color: (" + newColor.getRed() + ", " + 
					newColor.getGreen() + ", " + newColor.getBlue() + ").");
		}
	}
}
