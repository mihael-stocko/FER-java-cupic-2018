package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Line;

/**
 * This is a concrete implementation of {@link GeometricalObjectEditor} for lines.
 * 
 * @author Mihael Stočko
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Line this editor was provided by
	 */
	Line line;
	
	/**
	 * Starting x coordinate
	 */
	JTextField point1x;
	
	/**
	 * Starting y coordinate
	 */
	JTextField point1y;
	
	/**
	 * Ending x coordinate
	 */
	JTextField point2x;
	
	/**
	 * Ending y coordinate
	 */
	JTextField point2y;
	
	/**
	 * Red component of the color
	 */
	JTextField colorR;
	
	/**
	 * Green component of the color
	 */
	JTextField colorG;
	
	/**
	 * Blue component of the color
	 */
	JTextField colorB;
	
	/**
	 * New starting x coordinate
	 */
	int x1;
	
	/**
	 * New starting y coordinate
	 */
	int y1;
	
	/**
	 * New ending x coordinate
	 */
	int x2;
	
	/**
	 * New ending y coordinate
	 */
	int y2;
	
	/**
	 * New r component
	 */
	int r;
	
	/**
	 * New g component
	 */
	int g;
	
	/**
	 * New b component
	 */
	int b;
	
	/**
	 * Constructor. Reads current data from the JPanel.
	 */
	public LineEditor(Line line) {
		super();
		this.line = line;
		
		setLayout(new GridLayout(3, 4));
		
		point1x = new JTextField(5);
		point1y = new JTextField(5);
		point2x = new JTextField(5);
		point2y = new JTextField(5);
		colorR = new JTextField(5);
		colorG = new JTextField(5);
		colorB = new JTextField(5);
		add(new JLabel("Start X:", SwingConstants.RIGHT));
		add(point1x);
		add(new JLabel("Start Y:", SwingConstants.RIGHT));
		add(point1y);
		add(new JLabel("End X:", SwingConstants.RIGHT));
		add(point2x);
		add(new JLabel("End Y:", SwingConstants.RIGHT));
		add(point2y);
		add(new JLabel("Color RBG:", SwingConstants.RIGHT));
		add(colorR);
		add(colorG);
		add(colorB);
		
		point1x.setText(Integer.valueOf(line.getX1()).toString());
		point1y.setText(Integer.valueOf(line.getY1()).toString());
		point2x.setText(Integer.valueOf(line.getX2()).toString());
		point2y.setText(Integer.valueOf(line.getY2()).toString());
		colorR.setText(Integer.valueOf(line.getColor().getRed()).toString());
		colorG.setText(Integer.valueOf(line.getColor().getGreen()).toString());
		colorB.setText(Integer.valueOf(line.getColor().getBlue()).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		x1 = Integer.parseInt(point1x.getText());
		y1 = Integer.parseInt(point1y.getText());
		x2 = Integer.parseInt(point2x.getText());
		y2 = Integer.parseInt(point2y.getText());
		r = Integer.parseInt(colorR.getText());
		g = Integer.parseInt(colorG.getText());
		b = Integer.parseInt(colorB.getText());
		if(r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
			throw new IllegalStateException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		line.setX1(x1);
		line.setX2(x2);
		line.setY1(y1);
		line.setY2(y2);
		Color c = new Color(r, g, b);
		line.setColor(c);
	}
}
