package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This program demonstrates how CalcLayout manages components.
 * 
 * @author Mihael Stočko
 *
 */
public class CalcLayoutDemo extends JFrame {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public CalcLayoutDemo() {
		setLocation(20, 50);
		setSize(400, 200);
		setTitle("Layout demo!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * This method initializes the graphical user interface.
	 */
	private void initGUI() {
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("x");
		label.setBackground(Color.PINK);
		label.setOpaque(true);
		
		JLabel label2 = new JLabel("y");
		label2.setBackground(Color.YELLOW);
		label2.setOpaque(true);
		
		p.add(label, new RCPosition(1,1));
		p.add(label2, new RCPosition(2,3));
		p.add(new JLabel("z"), "2,7");
		p.add(new JLabel("w"), "4,2");
		p.add(new JLabel("a"), "4,5");
		p.add(new JLabel("b"), "4,7");

		getContentPane().add(p);
	}
	
	/**
	 * Main method. Creates a window and makes it visible.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new CalcLayoutDemo();
			frame.setVisible(true);
		});
	}
}
