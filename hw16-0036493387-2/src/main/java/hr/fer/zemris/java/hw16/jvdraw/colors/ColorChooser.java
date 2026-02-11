package hr.fer.zemris.java.hw16.jvdraw.colors;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is a frame that enables the user to choose from a palette of colors.
 * 
 * @author Mihael Stočko
 *
 */
public class ColorChooser extends JFrame implements ChangeListener {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * Color chooser object.
	 */
	protected JColorChooser tcc;
	
	/**
	 * JColorArea this ColorChooser is choosing the color for.
	 */
	private JColorArea area;
	
	/**
	 * Constructor.
	 */
	public ColorChooser(JColorArea area) {
		setSize(620, 400);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		this.area = area;
		
		tcc = new JColorChooser();
		tcc.getSelectionModel().addChangeListener(this);
		getContentPane().add(tcc);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		area.setSelectedColor(tcc.getColor());
	}
}
