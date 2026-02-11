package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Circle;

/**
 * This is a concrete implementation of {@link GeometricalObjectEditor} for circles.
 * 
 * @author Mihael Stočko
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	public static final long serialVersionUID = 1L;
	
	/**
	 * The cirlce this editor was provided by.
	 */
	Circle circle;
	
	/**
	 * Center x coordinate
	 */
	JTextField centerX;
	
	/**
	 * Center y coordinate
	 */
	JTextField centerY;
	
	/**
	 * Radius
	 */
	JTextField radius;
	
	/**
	 * Color R component
	 */
	JTextField colorR;
	
	/**
	 * Color G component
	 */
	JTextField colorG;
	
	/**
	 * Color B component
	 */
	JTextField colorB;
	
	/**
	 * New center x coordinate
	 */
	int cx;
	
	/**
	 * New center y coordinate
	 */
	int cy;
	
	/**
	 * New radius
	 */
	int rad;
	
	/**
	 * New color R component
	 */
	int r;
	
	/**
	 * New color G component
	 */
	int g;
	
	/**
	 * New color B component
	 */
	int b;
	
	/**
	 * Constructor. Reads current data from the JPanel.
	 */
	public CircleEditor(Circle circle) {
		super();
		this.circle = circle;
		
		setLayout(new GridLayout(3, 4));
		
		centerX = new JTextField(5);
		centerY = new JTextField(5);
		radius = new JTextField(5);
		colorR = new JTextField(5);
		colorG = new JTextField(5);
		colorB = new JTextField(5);
		
		add(new JLabel("Center X:", SwingConstants.RIGHT));
		add(centerX);
		add(new JLabel("Center Y:", SwingConstants.RIGHT));
		add(centerY);
		add(new JLabel("Radius:", SwingConstants.RIGHT));
		add(radius);
		add(new JLabel());
		add(new JLabel());
		add(new JLabel("Color RBG:", SwingConstants.RIGHT));
		add(colorR);
		add(colorG);
		add(colorB);
		
		centerX.setText(Integer.valueOf(circle.getC1()).toString());
		centerY.setText(Integer.valueOf(circle.getC2()).toString());
		radius.setText(Integer.valueOf((int)circle.getR()).toString());
		colorR.setText(Integer.valueOf(circle.getColor().getRed()).toString());
		colorG.setText(Integer.valueOf(circle.getColor().getGreen()).toString());
		colorB.setText(Integer.valueOf(circle.getColor().getBlue()).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		cx = Integer.parseInt(centerX.getText());
		cy = Integer.parseInt(centerY.getText());
		rad = Integer.parseInt(radius.getText());
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
		circle.setC1(cx);
		circle.setC2(cy);
		circle.setR(rad);
		Color c = new Color(r, g, b);
		circle.setColor(c);
	}
}
