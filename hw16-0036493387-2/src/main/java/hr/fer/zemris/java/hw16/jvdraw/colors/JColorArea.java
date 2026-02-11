package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This component shows a square colored with the selected color. It can be clicked to
 * change the color.
 * 
 * @author Mihael Stočko
 *
 */
public class JColorArea extends JComponent implements IColorProvider, ChangeListener {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * Selected color.
	 */
	private Color selectedColor;
	
	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> listeners = new LinkedList<>();
	
	/**
	 * Constructor.
	 */
	public JColorArea(Color selectedColor) {
		super();
		this.selectedColor = selectedColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	/**
	 * Getter for selectedColor
	 */
	public Color getSelectedColor() {
		return selectedColor;
	}

	/**
	 * Setter for selectedColor
	 */
	public void setSelectedColor(Color selectedColor) {
		notifyListeners(selectedColor);
		this.selectedColor = selectedColor;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "null cannot be added to listeners.");
		listeners.add(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "null cannot be removed from listeners.");
		listeners.remove(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		selectedColor = ((JColorChooser)arg0.getSource()).getColor();
	}
	
	/**
	 * Notifies all listeners that a change of color has occurred.
	 * 
	 * @param newColor New color
	 */
	private void notifyListeners(Color newColor) {
		for(ColorChangeListener l : listeners) {
			l.newColorSelected(this, selectedColor, newColor);
		}
	}
}
