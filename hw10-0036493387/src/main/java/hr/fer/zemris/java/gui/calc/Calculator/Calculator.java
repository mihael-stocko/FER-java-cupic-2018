package hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.BinaryOperators;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.CalcValueListener;
import hr.fer.zemris.java.gui.calc.ResultLabel;
import hr.fer.zemris.java.gui.calc.UnaryOperators;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * A simple calculator. Supported binary operations are addition, subtraction, multiplication,
 * division and exponentiation. Supported unary operations are sin, cos, tan, ctg, log, ln.
 * It also offers a stack for storing values.
 * 
 * @author Mihael Stočko
 *
 */
public class Calculator extends JFrame {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * Border of the display screen label.
	 */
	public static final int resultsBorder = 10;
	
	/**
	 * Is the Inv key enabled
	 */
	private boolean invEnabled = false;
	
	/**
	 * Stack for storing values
	 */
	private Stack<Double> stack = new Stack<Double>();
	
	/**
	 * Constructor.
	 */
	public Calculator() {
		setTitle("Calculator");
		setLocation(20, 20);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * This method initializes the graphical user interface.
	 */
	private void initGUI() {
		JPanel jp = new JPanel(new CalcLayout(3));
		CalcModelImpl model = new CalcModelImpl();
		
		JLabel results = new ResultLabel("0");
		results.setPreferredSize(new Dimension(340, 60));
		results.setOpaque(true);
		results.setBackground(Color.YELLOW);
		results.setBorder(BorderFactory.createEmptyBorder(resultsBorder, resultsBorder, 
				resultsBorder, resultsBorder));
		results.setHorizontalAlignment(SwingConstants.RIGHT);
		results.setFont(new Font("Serif", Font.PLAIN, 28));
		model.addCalcValueListener((CalcValueListener)results);
		jp.add(results, new RCPosition(1, 1));
		
		
		
		JButton button1 = new ButtonDigit(1);
		jp.add(button1, new RCPosition(4, 3));
		JButton button2 = new ButtonDigit(2);
		jp.add(button2, new RCPosition(4, 4));
		JButton button3 = new ButtonDigit(3);
		jp.add(button3, new RCPosition(4, 5));
		JButton button4 = new ButtonDigit(4);
		jp.add(button4, new RCPosition(3, 3));
		JButton button5 = new ButtonDigit(5);
		jp.add(button5, new RCPosition(3, 4));
		JButton button6 = new ButtonDigit(6);
		jp.add(button6, new RCPosition(3, 5));
		JButton button7 = new ButtonDigit(7);
		jp.add(button7, new RCPosition(2, 3));
		JButton button8 = new ButtonDigit(8);
		jp.add(button8, new RCPosition(2, 4));
		JButton button9 = new ButtonDigit(9);
		jp.add(button9, new RCPosition(2, 5));
		JButton button0 = new ButtonDigit(0);
		jp.add(button0, new RCPosition(5, 3));
		ActionListener actionDigit = a -> model.insertDigit(((ButtonDigit)a.getSource()).getDigit());
		button1.addActionListener(actionDigit);
		button2.addActionListener(actionDigit);
		button3.addActionListener(actionDigit);
		button4.addActionListener(actionDigit);
		button5.addActionListener(actionDigit);
		button6.addActionListener(actionDigit);
		button7.addActionListener(actionDigit);
		button8.addActionListener(actionDigit);
		button9.addActionListener(actionDigit);
		button0.addActionListener(actionDigit);
		
		
		
		JButton plusMinus = new JButton("+/-");
		jp.add(plusMinus, new RCPosition(5, 4));
		plusMinus.addActionListener(a -> model.swapSign());
		
		JButton dot = new JButton(".");
		jp.add(dot, new RCPosition(5, 5));
		dot.addActionListener(a -> model.insertDecimalPoint());
		
		JButton clr = new JButton("clr");
		jp.add(clr, new RCPosition(1, 7));
		clr.addActionListener(a -> model.clear());
		
		JButton res = new JButton("res");
		jp.add(res, new RCPosition(2, 7));
		res.addActionListener(a -> model.clearAll());
		
		JButton equals = new JButton("=");
		jp.add(equals, new RCPosition(1, 6));
		equals.addActionListener(a -> {
			try {
				model.compute();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(
						this, 
						"NaN.", 
						"NaN",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JCheckBox inv = new JCheckBox("Inv");
		jp.add(inv, new RCPosition(5, 7));
		inv.addActionListener(a -> invEnabled = !invEnabled);
		
		
		
		JButton add = new JButton("+");
		jp.add(add, new RCPosition(5, 6));
		add.addActionListener(a -> {
			try {
				model.setPendingBinaryOperation(BinaryOperators.ADD);
				model.clear();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(
						this, 
						"NaN.", 
						"NaN",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton subtract = new JButton("-");
		jp.add(subtract, new RCPosition(4, 6));
		subtract.addActionListener(a -> {
			try {
				model.setPendingBinaryOperation(BinaryOperators.SUB);
				model.clear();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(
						this, 
						"NaN.", 
						"NaN",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton multiply = new JButton("*");
		jp.add(multiply, new RCPosition(3, 6));
		multiply.addActionListener(a -> {
			try {
				model.setPendingBinaryOperation(BinaryOperators.MUL);
				model.clear();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(
						this, 
						"NaN.", 
						"NaN",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton divide = new JButton("/");
		jp.add(divide, new RCPosition(2, 6));
		divide.addActionListener(a -> {
			try {
				model.setPendingBinaryOperation(BinaryOperators.DIV);
				model.clear();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(
						this, 
						"NaN.", 
						"NaN",
						JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		JButton power = new JButton("x^n");
		jp.add(power, new RCPosition(5, 1));
		power.addActionListener(a -> {
			try {
				if(!invEnabled) {
					model.setPendingBinaryOperation(BinaryOperators.POW);
				} else {
					model.setPendingBinaryOperation(BinaryOperators.ROOT);
				}
				model.clear();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(
						this, 
						"NaN.", 
						"NaN",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		
		
		JButton sin = new ButtonFunction(UnaryOperators.sin, UnaryOperators.sinInv, "sin");
		jp.add(sin, new RCPosition(2, 2));
		
		JButton cos = new ButtonFunction(UnaryOperators.cos, UnaryOperators.cosInv, "cos");
		jp.add(cos, new RCPosition(3, 2));
		
		JButton tan = new ButtonFunction(UnaryOperators.tan, UnaryOperators.tanInv, "tan");
		jp.add(tan, new RCPosition(4, 2));
		
		JButton ctg = new ButtonFunction(UnaryOperators.ctg, UnaryOperators.ctgInv, "ctg");
		jp.add(ctg, new RCPosition(5, 2));
		
		JButton oneOverX = new ButtonFunction(UnaryOperators.oneOverX, UnaryOperators.oneOverX, "1/x");
		jp.add(oneOverX, new RCPosition(2, 1));
		
		JButton log = new ButtonFunction(UnaryOperators.log, UnaryOperators.logInv, "log");
		jp.add(log, new RCPosition(3, 1));
		
		JButton ln  = new ButtonFunction(UnaryOperators.ln, UnaryOperators.lnInv, "ln");
		jp.add(ln, new RCPosition(4, 1));
		
		ActionListener actionFunction = a -> {
			Double r = ((ButtonFunction)a.getSource()).execute(model.getValue(), invEnabled);
			if(!r.equals(Double.NaN)) {
				model.setValue(r);	
			} else {
				JOptionPane.showMessageDialog(
						this, 
						"NaN.", 
						"NaN",
						JOptionPane.ERROR_MESSAGE);
			}
		};
		
		sin.addActionListener(actionFunction);
		cos.addActionListener(actionFunction);
		tan.addActionListener(actionFunction);
		ctg.addActionListener(actionFunction);
		oneOverX.addActionListener(actionFunction);
		log.addActionListener(actionFunction);
		ln.addActionListener(actionFunction);
		
		
		
		JButton push = new JButton("push");
		jp.add(push, new RCPosition(3, 7));
		push.addActionListener(a -> stack.push(model.getValue()));
		
		JButton pop = new JButton("pop");
		jp.add(pop, new RCPosition(4, 7));
		pop.addActionListener(a -> {
			try {
				model.setValue(stack.pop());
			} catch(EmptyStackException e) {
				JOptionPane.showMessageDialog(
						this, 
						"The stack is empty.", 
						"Empty stack",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		
		
		getContentPane().add(jp);
		setSize(jp.getPreferredSize());
		setMinimumSize(jp.getMinimumSize());
		setMaximumSize(jp.getMaximumSize());
	}

	/**
	 * Main method. Creates a window and makes it visible.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame calc = new Calculator();
			calc.setVisible(true);
		});
	}
}
