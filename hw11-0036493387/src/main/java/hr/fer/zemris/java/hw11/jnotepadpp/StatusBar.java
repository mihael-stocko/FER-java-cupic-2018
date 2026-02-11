package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * A bar that shows some information about an opened document as well as date and time.
 * 
 * @author Mihael Stočko
 *
 */
public class StatusBar extends JPanel implements CaretListener {
	
	public static final long serialVersionUID = 1L;

	/**
	 * Length of the document
	 */
	private int length = 0;
	
	/**
	 * Position of the caret - line
	 */
	private int ln = 0;
	
	/**
	 * Position of the caret - column
	 */
	private int col = 0;
	
	/**
	 * Length of selected text
	 */
	private int sel = 0;
	
	/**
	 * Label that shows the length of the document
	 */
	private JLabel lengthLabel;
	
	/**
	 * Label that shows information about the caret
	 */
	private JLabel caretLabel;
	
	/**
	 * SingleDocumentModel object of the opened document
	 */
	private SingleDocumentModel model;
	
	/**
	 * Localization provider
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Localization listener
	 */
	private ILocalizationListener listener;
	
	/**
	 * Current time as string
	 */
	private String time;
	
	/**
	 * Label that shows the clock
	 */
	private JLabel clock;
	
	/**
	 * Formatter for date
	 */
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	/**
	 * Formatter for time
	 */
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	/**
	 * Should the thread that determines time shut down
	 */
	private boolean stopRequested = false;
	
	/**
	 * Constructor
	 * 
	 * @param flp Localization provider
	 * @param frame Window
	 */
	public StatusBar(FormLocalizationProvider flp, JFrame frame) {
		Objects.requireNonNull(flp, "FormLocalizationProvider cannot be null.");
		this.flp = flp;
		
		setPreferredSize(new Dimension(0, 20));
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BorderLayout());
		
		clock = new JLabel(time);
		add(clock, BorderLayout.LINE_END);
		
		JPanel stats = new JPanel();
		stats.setLayout(new GridLayout(1, 2));
		
		lengthLabel = new JLabel("length: " + length);
		lengthLabel.setPreferredSize(new Dimension(150, 0));
		stats.add(lengthLabel);
		
		caretLabel = new JLabel("Ln: " + ln + "   " + "Col: " + col + "   " + "Sel: " + sel);
		caretLabel.setPreferredSize(new Dimension(150, 0));
		stats.add(caretLabel);
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				lengthLabel.setText(flp.getString("length") + ": " + length);
				caretLabel.setText(flp.getString("ln") + ": " + ln + "   " + 
									flp.getString("col") + ": " + col + "   " + 
									flp.getString("sel") + ": " + sel);
			}
		};
		
		flp.addLocalizationListener(listener);
		
		add(stats, BorderLayout.LINE_START);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				stopRequested = true;
				super.windowClosed(arg0);
			}
		});
		
		updateTime();
		Thread t = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(500);
				} catch(Exception ex) {}
				if(stopRequested) break;
				SwingUtilities.invokeLater(()->{
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Updates the current time and sets the text on the clock label.
	 */
	private void updateTime() {
		time = LocalDate.now().format(dateFormatter) + " " + LocalTime.now().format(timeFormatter);
		clock.setText(time);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
		updateState();
	}
	
	/**
	 * Updates the current model that is being observed
	 * 
	 * @param model A new model
	 */
	public void updateModel(SingleDocumentModel model) {
		this.model = model;
		updateState();
	}
	
	/**
	 * Updates all its data for the new model
	 */
	private void updateState() {
		JTextArea textArea = model.getTextComponent();
		sel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		DocumentStats stats = Util.calculateDocumentStats(model.getTextComponent().getDocument());
		
		int offset = Math.min(
				textArea.getCaret().getDot(), 
				textArea.getCaret().getMark());
		
		String text = textArea.getText();
		char[] textChars = text.toCharArray();
		col = 0;
		for(int i = 0; i < offset; i++) {
			char c = textChars[i];
			if(c == '\n') {
				col = 0;
			}
			col++;
		}
		ln = 0;
		for(int i = 0; i < offset; i++) {
			char c = textChars[i];
			if(c == '\n') {
				ln++;
			}
		}
		
		length = stats.getCharacters();
		
		lengthLabel.setText(flp.getString("length") + ": " + length);
		caretLabel.setText(flp.getString("ln") + ": " + ln + "   " + 
				flp.getString("col") + ": " + col + "   " + 
				flp.getString("sel") + ": " + sel);
	}
}
