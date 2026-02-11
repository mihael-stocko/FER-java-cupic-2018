package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This program creates a window with two scrolling lists and one button. When the button
 * is clicked, the internal ListModel generates a new prime number. Both lists that are observers
 * of the list model then show the generated number.
 * 
 * @author Mihael Stočko
 *
 */
public class PrimDemo extends JFrame {
	
	public static final long serialVersionUID = 1l;
	
	/**
	 * Width and heigth of the lists
	 */
	private static final int listDimension = 200;
	
	/**
	 * Constructor.
	 */
	public PrimDemo() {
		setTitle("PrimDemo");
		setLocation(20, 20);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * An implementation of the ListModel interface parameterized by Integer.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	public static class PrimListModel implements ListModel<Integer> {
		
		/**
		 * List of all primes generated.
		 */
		private List<Integer> elements = new ArrayList<>();
		
		/**
		 * List of all registered listeners.
		 */
		private List<ListDataListener> listeners = new ArrayList<>();
		
		/**
		 * Constructor. Add 1 to the list of primes.
		 */
		public PrimListModel() {
			elements.add(1);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addListDataListener(ListDataListener arg0) {
			listeners.add(arg0);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeListDataListener(ListDataListener arg0) {
			listeners.remove(arg0);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getSize() {
			return elements.size();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer getElementAt(int arg0) {
			return elements.get(arg0);
		}
		
		/**
		 * Generates a new prime and adds it to the list. Notifies all observers.
		 */
		public void next() {
			int pos = elements.size();
			
			int last = getElementAt(getSize()-1);
			while(true) {
				last += 1;
				if(isPrime(last)) {
					break;
				}
			}
			
			elements.add(last);
			
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for(ListDataListener l : listeners) {
				l.intervalAdded(event);
			}
		}
		
		/**
		 * Checks if the given integer is prime.
		 * 
		 * @return <code>true</code> if the given integer is prime, <code>false</code> otherwise.
		 */
		private boolean isPrime(int n) {
			if(n <= 1) {
				return false;
			} else if(n <= 3) {
				return true;
			} else if(n % 2 == 0 || n % 3 == 0) {
				return false;
			}
	    
			int i = 5;
	     
			while(i*i <= n) {
				if (n % i == 0 || n % (i + 2) == 0) {
					return false;
				}
			    i += 6;
			}
			
			return true;
		}
	}
	
	/**
	 * This method initializes the graphical user interface.
	 */
	private void initGUI() {
		JPanel panel = new JPanel(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		JScrollPane scroll1 = new JScrollPane(list1);
		JScrollPane scroll2 = new JScrollPane(list2);
		scroll1.setPreferredSize(new Dimension(listDimension, listDimension));
		scroll2.setPreferredSize(new Dimension(listDimension, listDimension));
		JPanel listPanel = new JPanel(new GridLayout(1, 0));
		listPanel.add(scroll1);
		listPanel.add(scroll2);
		
		panel.add(listPanel, BorderLayout.CENTER);
		
		JButton button = new JButton("Next");
		panel.add(button, BorderLayout.PAGE_END);
		button.addActionListener(a -> model.next());
		
		Container cp = getContentPane();
		cp.add(panel);
		setSize(cp.getPreferredSize());
	}
	
	/**
	 * Main method.
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
