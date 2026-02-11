package hr.fer.zemris.java.gui.layouts;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;

public class CalcLayoutTest {
	public static final double delta = 1e-6;
	
	@Test(expected=CalcLayoutException.class)
	public void testIndexOutOfBoundsRowsTooSmall() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JButton(), "0,6");
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testIndexOutOfBoundsRowsTooGreat() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JButton(), "6,6");
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testIndexOutOfBoundsColsTooSmall() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JButton(), "3,0");
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testIndexOutOfBoundsColsTooGreat() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JButton(), "3,8");
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testLongSlot() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JButton(), "1,3");
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testSameSlot() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JButton(), "2,6");
		layout.addLayoutComponent(new JButton(), "2,6");
	}
	
	@Test
	public void testPreferredSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, "2,2");
		p.add(l2, "3,3");
		Dimension dim = p.getPreferredSize();
		Assert.assertEquals(152, dim.getWidth(), delta);
		Assert.assertEquals(158, dim.getHeight(), delta);
	}
	
	@Test
	public void testPreferredSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		Assert.assertEquals(152, dim.getWidth(), delta);
		Assert.assertEquals(158, dim.getHeight(), delta);
	}
}
