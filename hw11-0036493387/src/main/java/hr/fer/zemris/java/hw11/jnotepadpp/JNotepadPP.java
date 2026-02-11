package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.*;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This is a simple text editor. It allows the user to work with multiple files at the same time.
 * It provides the user with some basic actions like opening a document from the disk, saving
 * a document, showing document statistics and some functions for editing text, like sorting and
 * changing letter case. The whole application is internationalized, and the language can be
 * chosen from the languages menu.
 * 
 * @author Mihael Stočko
 *
 */
public class JNotepadPP extends JFrame {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * Icon for non-saved documents
	 */
	public static ImageIcon redFloppy = null;
	
	/**
	 * Icon for saved documents
	 */
	public static ImageIcon blueFloppy = null;
	
	/**
	 * Exit action
	 */
	private Action exitAction;
	
	/**
	 * New document action
	 */
	private Action newDocumentAction;
	
	/**
	 * Open action
	 */
	private Action openAction;
	
	/**
	 * Save as action
	 */
	private Action saveAsAction;
	
	/**
	 * Save action
	 */
	private Action saveAction;
	
	/**
	 * Close action
	 */
	private Action closeAction;
	
	/**
	 * Copy action
	 */
	private Action copyAction;
	
	/**
	 * Cut action
	 */
	private Action cutAction;
	
	/**
	 * Paste action
	 */
	private Action pasteAction;
	
	/**
	 * Statistics action
	 */
	private Action statisticsAction;
	
	/**
	 * English action
	 */
	private Action english;
	
	/**
	 * German action
	 */
	private Action german;
	
	/**
	 * Croatian action
	 */
	private Action croatian;
	
	/**
	 * To upper case action
	 */
	private Action toUpperCase;
	
	/**
	 * To lower case action
	 */
	private Action toLowerCase;
	
	/**
	 * Invert case action
	 */
	private Action invertCase;
	
	/**
	 * Ascending action
	 */
	private Action ascending;
	
	/**
	 * Descending action
	 */
	private Action descending;
	
	/**
	 * Unique action
	 */
	private Action unique;
	
	/**
	 * Localization provider
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * To upper case menu item
	 */
	JMenuItem toUpperCaseItem;
	
	/**
	 * To lower case menu item
	 */
	JMenuItem toLowerCaseItem;
	
	/**
	 * Invert case menu item
	 */
	JMenuItem invertCaseItem;
	
	/**
	 * Constructor
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		setLocation(0, 0);
		setSize(800, 600);
		
		try {
			loadImages();
		} catch(IOException e) {
			throw new IllegalStateException("Cannot load icon images.");
		}
		
		initGUI();
	}
	
	/**
	 * This method initializes the GUI
	 */
	private void initGUI() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		getContentPane().setLayout(new BorderLayout());
		
		Container model = new DefaultMultipleDocumentModel();
		getContentPane().add(model, BorderLayout.CENTER);
		
		MultipleDocumentModel multiDocModel = (MultipleDocumentModel)model;
		
		StatusBar bar = new StatusBar(flp, this);
		
		multiDocModel.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				previousModel.getTextComponent().removeCaretListener(bar);
				currentModel.getTextComponent().addCaretListener(bar);
				bar.updateModel(currentModel);
				
				CaretListener caretListener = new CaretListener() {
					
					@Override
					public void caretUpdate(CaretEvent e) {
						int len = Math.abs(e.getDot() - e.getMark());
						if(len == 0) {
							toUpperCaseItem.setEnabled(false);
							toLowerCaseItem.setEnabled(false);
							invertCaseItem.setEnabled(false);
						} else {
							toUpperCaseItem.setEnabled(true);
							toLowerCaseItem.setEnabled(true);
							invertCaseItem.setEnabled(true);
						}
					}
				};
				
				previousModel.getTextComponent().removeCaretListener(caretListener);
				currentModel.getTextComponent().addCaretListener(caretListener);
				
				setWindowTitle(currentModel);
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkAllUnsavedDocuments(multiDocModel);
			}
		});
		
		createActions(multiDocModel);
		
		JToolBar toolBar = new JToolBar("Toolbar");
		toolBar.setFloatable(true);
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openAction));
		toolBar.add(new JButton(saveAsAction));
		toolBar.add(new JButton(saveAction));
		toolBar.add(new JButton(closeAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(exitAction));
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(new MenuAction("file", flp));
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(closeAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu(new MenuAction("edit", flp));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		
		JMenu toolsMenu = new JMenu(new MenuAction("tools", flp));
		JMenu changeCase = new JMenu(new MenuAction("case", flp));
		toolsMenu.add(changeCase);
		toUpperCaseItem = new JMenuItem(toUpperCase);
		toLowerCaseItem = new JMenuItem(toLowerCase);
		invertCaseItem = new JMenuItem(invertCase);
		toUpperCase.setEnabled(false);
		toLowerCaseItem.setEnabled(false);
		invertCaseItem.setEnabled(false);
		changeCase.add(toUpperCaseItem);
		changeCase.add(toLowerCaseItem);
		changeCase.add(invertCaseItem);
		JMenu sort = new JMenu(new MenuAction("sort", flp));
		toolsMenu.add(sort);
		sort.add(ascending);
		sort.add(descending);
		toolsMenu.add(new JMenuItem(unique));
		toolsMenu.addSeparator();
		toolsMenu.add(new JMenuItem(statisticsAction));
		
		JMenu languages = new JMenu(new MenuAction("languages", flp));
		languages.add(new JMenuItem(english));
		languages.add(new JMenuItem(croatian));
		languages.add(new JMenuItem(german));
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);
		menuBar.add(languages);
		this.setJMenuBar(menuBar);
		
		getContentPane().add(bar, BorderLayout.PAGE_END);
		
		multiDocModel.createNewDocument();
	}
	
	/**
	 * This method loads the images used for tabs
	 * 
	 * @throws IOException
	 */
	private void loadImages() throws IOException {
		InputStream is1 = this.getClass()
				.getResourceAsStream("icons/redFloppy.png");
		InputStream is2 = this.getClass()
				.getResourceAsStream("icons/blueFloppy.png");
		if(is1 == null || is2 == null) {
			throw new IllegalStateException("Cannot load icon images.");
		}
		
		byte[] bytes1 = is1.readAllBytes();
		byte[] bytes2 = is2.readAllBytes();
		is1.close();
		is2.close();
		
		redFloppy = new ImageIcon(bytes1);
		blueFloppy = new ImageIcon(bytes2);	
	}
	
	/**
	 * Sets the window title then the tab is changed
	 * 
	 * @param model The new tab
	 */
	private void setWindowTitle(SingleDocumentModel model) {
		String fileName;
		if(model.getFilePath() == null) {
			fileName = "New document";
		} else {
			fileName = model.getFilePath().toString();
		}
		
		setTitle(fileName + " - JNotepad++");
	}
	
	/**
	 * This is called when the user attempts to exit the program
	 * 
	 * @param model Tabbed pane
	 */
	public void checkAllUnsavedDocuments(MultipleDocumentModel model) {
		for(int i = 0; i < model.getNumberOfDocuments(); i++) {
			SingleDocumentModel doc = model.getDocument(i);
			if(!doc.isModified()) {
				continue;
			}
			Object[] options = {"Save changes",
                    		"Discard changes",
                    		"Cancel"};
			String fileName = doc.getFilePath() == null ? 
					"New document" : doc.getFilePath().getFileName().toString();
			int n = JOptionPane.showOptionDialog((Component)this.getContentPane(),
												"Would you like to save the changes in document:\n" + 
														fileName + "?",
												"Warning",
												JOptionPane.YES_NO_CANCEL_OPTION,
												JOptionPane.WARNING_MESSAGE,
												null,
												options,
												options[2]);
			if(n == -1 || n == 2) {
				return;
			}
			
			if(n == 0) {
				Path p;
				if(doc.getFilePath() != null) {
					p = doc.getFilePath();
				} else {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(JNotepadPP.this, 
								"Nothing was saved", 
								"Warning", 
								JOptionPane.WARNING_MESSAGE);
						continue;
					}
					p = jfc.getSelectedFile().toPath();
				}
				
				model.saveDocument(doc, p);
			
			}
		}
		dispose();
	}
	
	/**
	 * This creates all actions that are to be used.
	 * 
	 * @param multiDocModel Tabbed pane
	 */
	private void createActions(MultipleDocumentModel multiDocModel) {
		exitAction = new ExitAction("exit", flp, this, multiDocModel);
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		newDocumentAction = new NewDocumentAction("new", flp, multiDocModel);
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		openAction = new OpenAction("open", flp, this, multiDocModel);
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		saveAsAction = new SaveAsAction("saveas", flp, this, multiDocModel);
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		saveAction = new SaveAction("save", flp, this, multiDocModel);
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		closeAction = new CloseAction("close", flp, multiDocModel);
		closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		
		Clipboard clipboard = new Clipboard("");
		
		copyAction = new CopyAction("copy", flp, multiDocModel, clipboard);
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		cutAction = new CutAction("cut", flp, multiDocModel, clipboard);
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		pasteAction = new PasteAction("paste", flp, multiDocModel, clipboard);
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		
		statisticsAction = new StatisticsAction("stats", flp, this, multiDocModel);
		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		english = new English("english", flp);
		croatian = new Croatian("croatian", flp);
		german = new German("german", flp);
		
		toUpperCase = new ToUpperCase("toUpperCase", flp, multiDocModel);
		toLowerCase = new ToLowerCase("toLowerCase", flp, multiDocModel);
		invertCase = new InvertCase("invertCase", flp, multiDocModel);
		
		ascending = new Sort("ascending", flp, multiDocModel, false);
		descending = new Sort("descending", flp, multiDocModel, true);
		
		unique = new Unique("unique", flp, multiDocModel);
	}
	
	/**
	 * Main method
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
