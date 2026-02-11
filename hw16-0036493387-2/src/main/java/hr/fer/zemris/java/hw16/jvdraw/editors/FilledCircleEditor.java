package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FilledCircle;

/**
 * This is a concrete implementation of {@link GeometricalObjectEditor} for filled circles.
 * 
 * @author Mihael Stočko
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	public static final long serialVersionUID = 1L;
	
	/**
	 * The filled cirlce this editor was provided by.
	 */
	FilledCircle filledCircle;
	
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
	 * Color1 R component
	 */
	JTextField colorR1;
	
	/**
	 * Color1 G component
	 */
	JTextField colorG1;
	
	/**
	 * Color1 B component
	 */
	JTextField colorB1;
	
	/**
	 * Color2 R component
	 */
	JTextField colorR2;
	
	/**
	 * Color2 G component
	 */
	JTextField colorG2;
	
	/**
	 * Color2 B component
	 */
	JTextField colorB2;
	
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
	 * New color1 R component
	 */
	int r1;
	
	/**
	 * New color1 G component
	 */
	int g1;
	
	/**
	 * New color1 B component
	 */
	int b1;
	
	/**
	 * New color2 R component
	 */
	int r2;
	
	/**
	 * New color2 G component
	 */
	int g2;
	
	/**
	 * New color2 B component
	 */
	int b2;
	
	/**
	 * Constructor. Reads current data from the JPanel.
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super();
		this.filledCircle = filledCircle;
		
		setLayout(new GridLayout(4, 4));
		
		centerX = new JTextField(5);
		centerY = new JTextField(5);
		radius = new JTextField(5);
		colorR1 = new JTextField(5);
		colorG1 = new JTextField(5);
		colorB1 = new JTextField(5);
		colorR2 = new JTextField(5);
		colorG2 = new JTextField(5);
		colorB2 = new JTextField(5);
		
		add(new JLabel("Center X:", SwingConstants.RIGHT));
		add(centerX);
		add(new JLabel("Center Y:", SwingConstants.RIGHT));
		add(centerY);
		add(new JLabel("Radius:", SwingConstants.RIGHT));
		add(radius);
		add(new JLabel());
		add(new JLabel());
		add(new JLabel("Foreground color RBG:", SwingConstants.RIGHT));
		add(colorR1);
		add(colorG1);
		add(colorB1);
		add(new JLabel("Background color RBG:", SwingConstants.RIGHT));
		add(colorR2);
		add(colorG2);
		add(colorB2);
		
		centerX.setText(Integer.valueOf(filledCircle.getC1()).toString());
		centerY.setText(Integer.valueOf(filledCircle.getC2()).toString());
		radius.setText(Integer.valueOf((int)filledCircle.getR()).toString());
		colorR1.setText(Integer.valueOf(filledCircle.getColor1().getRed()).toString());
		colorG1.setText(Integer.valueOf(filledCircle.getColor1().getGreen()).toString());
		colorB1.setText(Integer.valueOf(filledCircle.getColor1().getBlue()).toString());
		colorR2.setText(Integer.valueOf(filledCircle.getColor2().getRed()).toString());
		colorG2.setText(Integer.valueOf(filledCircle.getColor2().getGreen()).toString());
		colorB2.setText(Integer.valueOf(filledCircle.getColor2().getBlue()).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		cx = Integer.parseInt(centerX.getText());
		cy = Integer.parseInt(centerY.getText());
		rad = Integer.parseInt(radius.getText());
		r1 = Integer.parseInt(colorR1.getText());
		g1 = Integer.parseInt(colorG1.getText());
		b1 = Integer.parseInt(colorB1.getText());
		r2 = Integer.parseInt(colorR2.getText());
		g2 = Integer.parseInt(colorG2.getText());
		b2 = Integer.parseInt(colorB2.getText());
		if(r1 < 0 || r1 > 255 || g1 < 0 || g1 > 255 || b1 < 0 || b1 > 255 || 
				r2 < 0 || r2 > 255 || g2 < 0 || g2 > 255 || b2 < 0 || b2 > 255) {
			throw new IllegalStateException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		filledCircle.setC1(cx);
		filledCircle.setC2(cy);
		filledCircle.setR(rad);
		Color c1 = new Color(r1, g1, b1);
		Color c2 = new Color(r2, g2, b2);
		filledCircle.setColor1(c1);
		filledCircle.setColor2(c2);
	}
}
