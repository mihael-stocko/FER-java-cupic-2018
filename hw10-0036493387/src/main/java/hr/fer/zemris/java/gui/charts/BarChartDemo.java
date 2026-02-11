package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This program demonstrates the BarChartComponent. It takes a single argument - the path
 * to a file that contains all relevant information for the creation of a chart. It then
 * opens a window and draws the graph into it.
 * 
 * @author Mihael Stočko
 *
 */
public class BarChartDemo extends JFrame {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Path to the file with information
	 */
	public static Path p;
	
	/**
	 * BarChart object
	 */
	public static BarChart model;
	
	/**
	 * Constructor.
	 */
	public BarChartDemo() {
		setTitle("BarChartDemo");
		setLocation(20, 20);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * This method initializes the graphical user interface.
	 */
	private void initGUI() {
		JPanel jp = new JPanel(new BorderLayout());
		JComponent chart = new BarChartComponent(model);
		chart.setOpaque(true);
		jp.add(chart, BorderLayout.CENTER);
		JLabel label = new JLabel(p.toString());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		jp.add(label, BorderLayout.NORTH);
		
		getContentPane().add(jp);
		pack();
		setMinimumSize(chart.getMinimumSize());
	}
	
	/**
	 * Main method
	 * 
	 * @param args path to the file with information
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("The program expects one argument.");
			return;
		}
		
		p = Paths.get(args[0]);
		
		if(!p.toFile().isFile()) {
			System.out.println("The given path must point to a file.");
			return;
		}
		
		List<String> strings;
		try {
			strings = Files.readAllLines(p);
		} catch(IOException e) {
			System.out.println("Cannot read the file.");
			return;
		}
		
		if(strings.size() != 6) {
			System.out.println("6 lines expected.");
			return;
		}
		
		List<XYValue> values = new ArrayList<>();
		String[] points = strings.get(2).split(" ");
		for(String p : points) {
			try {
				String[] s = p.split(",");
				if(s.length != 2) {
					throw new IllegalArgumentException("One point must have two values.");
				}
				
				values.add(new XYValue(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
			} catch(Exception e) {
				System.out.println(e.getMessage());
				return;
			}
		}
		
		String xAxis = strings.get(0);	
		String yAxis = strings.get(1);
		
		
		int yMin;
		int yMax;
		int step;
		try {
			yMin = Integer.parseInt(strings.get(3));
			yMax = Integer.parseInt(strings.get(4));
			step = Integer.parseInt(strings.get(5));
		} catch(Exception e) {
			System.out.println("Cannot parse yMin, yMax and/or step.");
			return;
		}
		
		model = new BarChart(values, xAxis, yAxis, yMin, yMax, step);
		
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo().setVisible(true);
		});
	}
}
