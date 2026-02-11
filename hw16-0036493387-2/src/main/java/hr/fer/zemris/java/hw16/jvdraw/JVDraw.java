package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChooser;
import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FPoly;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geomObjects.Point;
import hr.fer.zemris.java.hw16.jvdraw.list.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FPolyTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * This is the main program. This application is a simple paint clone. It offers the user
 * options for changing colors, drawing lines, circles and filled circles and exporting images.
 * 
 * @author Mihael Stočko
 *
 */
public class JVDraw extends JFrame {

	public static final long serialVersionUID = 1L;
	
	/**
	 * Is the file saved
	 */
	boolean saved = true;
	
	/**
	 * Path where the file is saved
	 */
	Path path;
	
	/**
	 * Drawing model
	 */
	private DrawingModelImpl dm;
	
	/**
	 * Canvas
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * Foreground color provider
	 */
	private JColorArea colorArea1 = new JColorArea(Color.RED);
	
	/**
	 * Background color provider
	 */
	private JColorArea colorArea2 = new JColorArea(Color.BLUE);
	
	/**
	 * Line tool
	 */
	public final LineTool lineTool = new LineTool(colorArea1);
	
	/**
	 * Circle tool
	 */
	public final CircleTool circleTool = new CircleTool(colorArea1);
	
	/**
	 * Filled circle tool
	 */
	public final FCircleTool fCircleTool = new FCircleTool(colorArea1, colorArea2);
	
	public final FPolyTool fPolyTool = new FPolyTool(colorArea1, colorArea2);
	
	/**
	 * Current tool
	 */
	private Tool currentTool = lineTool;
	
	/**
	 * Line button
	 */
	private JToggleButton button1;
	
	/**
	 * Circle button
	 */
	private JToggleButton button2;
	
	/**
	 * Filled circle button
	 */
	private JToggleButton button3;
	
	private JToggleButton button4;
	
	/**
	 * Open action
	 */
	private Action openAction;
	
	/**
	 * Save action
	 */
	private Action saveAction;
	
	/**
	 * Save as action
	 */
	private Action saveAsAction;
	
	/**
	 * Export action
	 */
	private Action exportAction;
	
	/**
	 * Exit action
	 */
	private Action exitAction;
	
	/**
	 * Constructor.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Exit();
			}
		});
		setTitle("JVDraw");
		setLocation(0, 0);
		setSize(800, 600);
		initGUI();
	}
	
	/**
	 * This method initialized the GUI.
	 */
	private void initGUI() {
		setLayout(new BorderLayout());
		
		colorArea1.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				SwingUtilities.invokeLater(() -> {
					new ColorChooser(colorArea1).setVisible(true);
				});
			}
		});
		
		colorArea2.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				SwingUtilities.invokeLater(() -> {
					new ColorChooser(colorArea2).setVisible(true);
				});
			}
		});
		
		JToolBar toolBar = new JToolBar("Toolbar");
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.setFloatable(true);
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		toolBar.add(colorArea1);
		toolBar.add(colorArea2);
		
		addButtonListeners();
		
		toolBar.add(button1);
		toolBar.add(button2);
		toolBar.add(button3);
		toolBar.add(button4);
		
		getContentPane().add(new StatusBar(colorArea1, colorArea2), BorderLayout.PAGE_END);
		
		dm = new DrawingModelImpl();
		dm.setForegroundColor(colorArea1.getCurrentColor());
		dm.setBackgroundColor(colorArea2.getCurrentColor());
		
		lineTool.setDm(dm);
		circleTool.setDm(dm);
		fCircleTool.setDm(dm);
		fPolyTool.setDm(dm);
		
		canvas = new JDrawingCanvas(dm);
		getContentPane().add(canvas, BorderLayout.CENTER);
		canvas.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				currentTool.mouseClicked(e);
				saved = false;
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				saved = false;
				currentTool.mousePressed(arg0);
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				saved = false;
				currentTool.mouseReleased(arg0);
			}
		});
		
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				currentTool.mouseMoved(e);
				canvas.repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				currentTool.mouseDragged(arg0);
				canvas.repaint();
			}
			
		});

		canvas.setTool(currentTool);
		
		DrawingObjectListModel listModel = new DrawingObjectListModel(dm);
		dm.addDrawingModelListener(listModel);
		JList<String> list = new JList<>(listModel);
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					listModel.remove(list.getSelectedIndex());
				} else if(e.getKeyCode() == KeyEvent.VK_PLUS) {
					listModel.moveUp(list.getSelectedIndex());
					list.setSelectedIndex(list.getSelectedIndex()-1);
				} else if(e.getKeyCode() == KeyEvent.VK_MINUS) {
					listModel.moveDown(list.getSelectedIndex());
					list.setSelectedIndex(list.getSelectedIndex()+1);
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					GeometricalObjectEditor editor = dm.getObject(list.getSelectedIndex())
							.createGeometricalObjectEditor();
					int result = JOptionPane.showConfirmDialog(null, editor, "Object editor", 
							JOptionPane.OK_CANCEL_OPTION);
					if(result == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "Invalid parameters have been given.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(200, 0));
		
		getContentPane().add(scrollPane, BorderLayout.LINE_END);
		
		createActions();
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(exportAction));
		fileMenu.add(new JMenuItem(exitAction));
		this.setJMenuBar(menuBar);
	}

	/**
	 * This method initializes all actions.
	 */
	private void createActions() {
		openAction = new AbstractAction() {
			public static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Path p = getOpenPath();
				if(p == null) {
					return;
				}
				if(!p.toString().endsWith(".jvd")) {
					JOptionPane.showMessageDialog(null, "Cannot open file", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				List<String> lines = null;
				try {
					lines = Files.readAllLines(p);
				} catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Cannot open file", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				List<GeometricalObject> objects = new LinkedList<>();
				try {
					for(String l : lines) {
						String[] parsed = l.split(" ");
						switch(parsed[0]) {
						case "LINE":
							if(parsed.length != 8) {
								throw new RuntimeException();
							}
							Line line = new Line(Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]),
									Integer.parseInt(parsed[3]), Integer.parseInt(parsed[4]));
							line.setColor(new Color(Integer.parseInt(parsed[5]), Integer.parseInt(parsed[7]), 
									Integer.parseInt(parsed[7])));
							objects.add(line);
							break;
						case "CIRCLE":
							if(parsed.length != 7) {
								throw new RuntimeException();
							}
							Circle circle = new Circle(Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]), 
									Integer.parseInt(parsed[3]));
							circle.setColor(new Color(Integer.parseInt(parsed[4]), Integer.parseInt(parsed[5]), 
									Integer.parseInt(parsed[6])));
							objects.add(circle);
							break;
						case "FCIRCLE":
							if(parsed.length != 10) {
								throw new RuntimeException();
							}
							FilledCircle filledCircle = new FilledCircle(Integer.parseInt(parsed[1]), 
									Integer.parseInt(parsed[2]), Integer.parseInt(parsed[3]));
							filledCircle.setColor1(new Color(Integer.parseInt(parsed[4]), 
									Integer.parseInt(parsed[5]), Integer.parseInt(parsed[6])));
							filledCircle.setColor2(new Color(Integer.parseInt(parsed[7]), 
									Integer.parseInt(parsed[8]), Integer.parseInt(parsed[9])));
							objects.add(filledCircle);
							break;
						case "FPOLY":
							List<Point> points = new LinkedList<>();
							int n = Integer.parseInt(parsed[1]);
							System.out.println(n);
							for(int i = 0; i < n; i++) {
								points.add(new Point(Integer.parseInt(parsed[2+i*2]), Integer.parseInt(parsed[2+i*2+1])));
								System.out.println(points.get(points.size()-1).getX() + " " + points.get(points.size()-1).getY());
							}
							
							FPoly fpoly = new FPoly(points);
							
							fpoly.setColor1(new Color(Integer.parseInt(parsed[parsed.length-6]), 
									Integer.parseInt(parsed[parsed.length-5]), Integer.parseInt(parsed[parsed.length-4])));
							
							
							fpoly.setColor2(new Color(Integer.parseInt(parsed[parsed.length-3]), 
									Integer.parseInt(parsed[parsed.length-2]), Integer.parseInt(parsed[parsed.length-1])));
							
							System.out.println(fpoly.getColor1());
							System.out.println(fpoly.getColor2());
							dm.add(fpoly);
							
						default:
							throw new RuntimeException();
						}
					}
				} catch(Exception e) {
//					JOptionPane.showMessageDialog(null, "Cannot open file", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!saved) {
					int result = JOptionPane.showConfirmDialog(null, "Save file?", 
							"Warning", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						saveAction.actionPerformed(null);
					}
				}
				
				int n = dm.getSize();
				for(int i = 0; i < n; i++) {
					dm.remove(dm.getObject(0));
				}
				
				for(GeometricalObject o : objects) {
					dm.add(o);
				}
				
				saved = true;
			}
		};
		
		saveAction = new AbstractAction() {
			public static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(path != null) {
					save(path);
				} else {
					Path newPath = getSavePath();
					if(newPath != null) {
						save(newPath);
						saved = true;
						path = newPath;
					}
				}
			}
		};
		
		saveAsAction = new AbstractAction() {
			public static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Path newPath = getSavePath();
				if(newPath != null) {
					save(newPath);
					saved = true;
					path = newPath;
				}
			}
		};
		
		exportAction = new AbstractAction() {
			public static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GeometricalObjectBBCalculator v = new GeometricalObjectBBCalculator();
				int n = dm.getSize();
				for(int i = 0; i < n; i++) {
					dm.getObject(i).accept(v);
				}
				
				Rectangle r = v.getBoundingBox();
				
				String[] buttons = { "jpg", "png", "gif"};
				int result = JOptionPane.showOptionDialog(null, "Choose image type", "Image type",
				        JOptionPane.INFORMATION_MESSAGE, 0, null, buttons, buttons[0]);
				
				String extension;
				switch(result) {
				case 0:
					extension = "jpg";
					break;
				case 1:
					extension = "png";
					break;
				case 2:
					extension = "gif";
					break;
				default:
					return;
				}
				
				Path p = getExportPath();
				if(p == null) {
					return;
				}
				
				BufferedImage image = new BufferedImage(r.width, r.height, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, image.getWidth(), image.getHeight());
				g.translate(-r.getX(), -r.getY());
				
				GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
				for(int i = 0; i < n; i++) {
					dm.getObject(i).accept(painter);
				}
				
				p = Paths.get(p.toString() + "." + extension);
				try {
					ImageIO.write(image, extension, p.toFile());
				} catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Cannot export file", 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
		exitAction = new AbstractAction() {
			public static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Exit();
			}
		};
		
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openAction.putValue(Action.NAME, "Open");
		
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.NAME, "Save");
		
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsAction.putValue(Action.NAME, "Save as...");
		
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exportAction.putValue(Action.NAME, "Export");
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.NAME, "Exit");
	}
	
	/**
	 * This method saves the current state of the canvas to the given path.
	 * 
	 * @param p Path
	 */
	private void save(Path p) {
		try {
			StringBuilder sb = new StringBuilder();
			int size = dm.getSize();
			for(int i = 0; i < size; i++) {
				sb.append(prepareString(dm.getObject(i)));
				if(i < size-1) {
					sb.append("\n");
				}			
			}
			Files.write(p, sb.toString().getBytes());
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot save file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Gets the path for saving by using {@link JFileChooser}.
	 * 
	 * @return Chosen path
	 */
	private Path getSavePath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save file");
		if(fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		Path newPath = Paths.get(filePath.toString() + ".jvd");
		if(newPath.toFile().exists()) {
			int result = JOptionPane.showConfirmDialog(null, "Overwrite?", 
					"Warning", JOptionPane.YES_NO_OPTION);
			if(result != JOptionPane.YES_OPTION) {
				return null;
			}
		}
		
		return newPath;
	}
	
	/**
	 * Gets the path for opening by using {@link JFileChooser}.
	 * 
	 * @return Chosen path
	 */
	private Path getOpenPath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if(fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		return filePath;
	}
	
	/**
	 * Gets the path for exporting by using {@link JFileChooser}.
	 * 
	 * @return Chosen path
	 */
	private Path getExportPath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Export file");
		if(fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		if(filePath.toFile().exists()) {
			int result = JOptionPane.showConfirmDialog(null, "Overwrite?", 
					"Warning", JOptionPane.YES_NO_OPTION);
			if(result != JOptionPane.YES_OPTION) {
				return null;
			}
		}
		
		return filePath;
	}
	
	/**
	 * Transforms the given object into its string representation for storing.
	 * 
	 * @return String representation of the given object
	 */
	private String prepareString(Object o) {
		String s;
		
		if(o instanceof Line) {
			Line line = (Line)o;
			s = "LINE " + line.getX1() + " " + line.getY1() + " " + 
				line.getX2() + " " + line.getY2() + " " + line.getColor().getRed() + " " + 
					line.getColor().getGreen() + " " + line.getColor().getBlue();
		} else if(o instanceof Circle) {
			Circle circle = (Circle)o;
			s = "CIRCLE " + circle.getC1() + " " + circle.getC2() + " " + (int)circle.getR() + " " +
			circle.getColor().getRed() + " " + circle.getColor().getGreen() + " " + 
					circle.getColor().getBlue();
		} else if(o instanceof FilledCircle) {
			FilledCircle filledCirle = (FilledCircle)o;
			s = "FCIRCLE " + filledCirle.getC1() + " " + filledCirle.getC2() + " " + 
			(int)filledCirle.getR() + " " + filledCirle.getColor1().getRed() + " " + 
					filledCirle.getColor1().getGreen() + " " + filledCirle.getColor1().getBlue() + " " +
					filledCirle.getColor2().getRed() + " " + filledCirle.getColor2().getGreen() + 
					" " + filledCirle.getColor2().getBlue();
		} else if(o instanceof FPoly) {
			FPoly fpoly = (FPoly)o;
			List<Point> points = fpoly.getPoints();
			s = "FPOLY " + points.size() + " ";
			for(Point p : points) {
				s += p.getX() + " " + p.getY() + " ";
			}
			s += fpoly.getColor1().getRed() +" " + fpoly.getColor1().getGreen() + " " + fpoly.getColor1().getBlue() + " ";
			s += fpoly.getColor2().getRed() + " " + fpoly.getColor2().getGreen() + 
					" " + fpoly.getColor2().getBlue();
		} else {
			throw new IllegalArgumentException();
		}
		
		return s;
	}
	
	/**
	 * Adds action listeners to buttons.
	 */
	private void addButtonListeners() {
		ButtonGroup group = new ButtonGroup();
		button1 = new JToggleButton("Line");
		button1.setSelected(true);
		button2 = new JToggleButton("Circle");
		button3 = new JToggleButton("Filled circle");
		button4 = new JToggleButton("FPoly");
		group.add(button1);
		group.add(button2);
		group.add(button3);
		group.add(button4);
		
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTool = lineTool;
				canvas.setTool(currentTool);
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTool = circleTool;
				canvas.setTool(currentTool);
			}
		});

		button3.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTool = fCircleTool;
				canvas.setTool(currentTool);
			}
		});
		
		button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTool = fPolyTool;
				canvas.setTool(currentTool);
			}
		});
	}
	
	/**
	 * Asks the user if they want to save the current document if it isn't saved already and
	 * then exits the application.
	 */
	public void Exit() {
		if(!saved) {
			int result = JOptionPane.showConfirmDialog(null, "Save file?", 
					"Warning", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				saveAction.actionPerformed(null);
			}
		}
		
		this.dispose();
	}
	
	/**
	 * Main method.
	 * @param args Not used
	 */
	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> {
//			new JVDraw().setVisible(true);
//		});
		new JVDraw().setVisible(true);
	}
}
