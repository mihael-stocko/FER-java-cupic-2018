package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a simple hashtable. It stores objects of type value
 * and maps them to objects of type key.
 * 
 * @author Mihael Stočko
 *
 * @param <K> Key
 * @param <V> Value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Internally used class that represents one entry of a hashtable.
	 * 
	 * @author Mihael Stočko
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Key
		 */
		private K key;
		
		/**
		 * Value
		 */
		private V value;
		
		/**
		 * Pointer to the next entry in the same slot.
		 */
		private TableEntry<K, V> next = null;
		
		/**
		 * Constructor
		 * 
		 * @param key
		 * @param value
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Getter for value
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Setter for value
		 * 
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Getter for key
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}
	}
	
	/**
	 * Array that keeps the entries.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Number of entries in the hashtable.
	 */
	private int size = 0;
	
	/**
	 * Number of modifications performed on the hashtable since its creation.
	 */
	private int modificationCount = 0;
	
	/**
	 * Default constructor
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[])new TableEntry<?, ?>[16];
	}
	
	/**
	 * Constructor
	 * 
	 * @param capacity Initial capacity of the hashtable
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be smaller than 1, was " + capacity + ".");
		}
		
		int i = 1;
		while(i < capacity) {
			i *= 2;
		}
		
		table = (TableEntry<K, V>[])new TableEntry<?, ?>[i];
	}
	
	/**
	 * Returns the number of entries in the hashtable.
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if the hashtable is empty.
	 * 
	 * @return <code>true</code> if the hashtable is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Calculates the slot for the entry to be inserted.
	 * 
	 * @param table Array that contains the entries.
	 * @param key Key of the element to be inserted
	 * @return Calculated slot
	 * @throws NullPointerException
	 */
	private int calculateSlot(TableEntry<K, V>[] table, Object key) {
		if(key == null) {
			throw new NullPointerException("Key cannot be null.");
		}
		
		return Math.abs(key.hashCode()) % table.length;
	}
	
	/**
	 * Checks if the hashtable contains the elements with the given key.
	 * 
	 * @param key
	 * @return <code>true</code> if the elements is in the hashatble, <code>false</code> otherwise.
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			return false;
		}
		
		int slot = calculateSlot(table, key);
		
		TableEntry<K, V> entry = table[slot];
		while(entry != null) {
			if(entry.getKey().equals(key)) {
				return true;
			} 
			entry = entry.next;
		}
		
		return false;
	}
	
	/**
	 * Checks if the hashtable contains the elements with the given value.
	 * 
	 * @param value
	 * @return <code>true</code> if the elements is in the hashatble, <code>false</code> otherwise.
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];
			while(entry != null) {
				if(entry.getValue().equals(value)) {
					return true;
				} 
				entry = entry.next;
			}
		}
		
		return false;
	}
	
	/**
	 * Adds the elements with the specified key and value to the hashtable.
	 * 
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new IllegalArgumentException("Key cannot be null.");
		}
		
		if(addToTable(table, key, value)) {
			size++;
		}
		
		if(size >= table.length*0.75) {
			resize();
		}
	}
	
	/**
	 * Internally used method that adds an elements with the specified key and value to the
	 * hashtable. Resizes the table if necessary.
	 * 
	 * @param table Table that contains the entries.
	 * @param key
	 * @param value
	 * @return <code>true</code> if a new entry has been added, <code>false</code> if an
	 * existing one has been modified.
	 */
	private boolean addToTable(TableEntry<K, V>[] table, K key, V value) {
		int slot = calculateSlot(table, key);
		
		TableEntry<K, V> entry = table[slot];
		if(entry == null) {
			table[slot] = new TableEntry<K, V>(key, value);
			modificationCount++;
			return true;
		}
		
		while(true) {
			if(entry.getKey().equals(key)) {
				entry.setValue(value);
				return false;
			} 
			if(entry.next == null) {
				break;
			}
			entry = entry.next;
		}
		
		entry.next = new TableEntry<K, V>(key, value);
		modificationCount++;
		return true;
	}
	
	/**
	 * Doubles the capacity of the array holding entries.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[])new TableEntry<?, ?>[table.length*2];
		
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];
			while(entry != null) {
				addToTable(newTable, entry.getKey(), entry.getValue());
				entry = entry.next;
			}
		}
		
		table = newTable;
	}
	
	/**
	 * Returns the value of the entry mapped under the specified key. If the is not in the hashtable,
	 * returns null.
	 * 
	 * @param key
	 * @return value for the given key, or null.
	 */
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		
		int slot = calculateSlot(table, key);
		
		TableEntry<K, V> entry = table[slot];
		while(entry != null) {
			if(entry.getKey().equals(key)) {
				return entry.getValue();
			} 
			entry = entry.next;
		}
		
		return null;
	}
	
	/**
	 * Removes the entry mapped under the given key form the hashtable.
	 * 
	 * @param key
	 * @throws IllegalArgumentException
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		if(!containsKey(key)) {
			throw new IllegalArgumentException("No such key in the hashtable, key was " + key + ".");
		}
		
		int slot = calculateSlot(table, key);
		
		if(table[slot].getKey().equals(key)) {
			table[slot] = table[slot].next;
			size--;
			modificationCount++;
			return;
		}
		
		TableEntry<K, V> entry = table[slot];
		while(entry != null && entry.next != null) {
			if(entry.next.getKey().equals(key)) {
				entry.next = entry.next.next;
				size--;
				modificationCount++;
				return;
				
			}
			if(entry.next.next != null) {
				entry = entry.next;
			} else {
				break;
			}
		}
		
		entry.next = null;
		size--;
		modificationCount++;
	}
	
	/**
	 * Returns a String representation of the hashtable.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int counter = size;
		
		forLoop : for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];
			while(entry != null) {
				sb.append(entry.key.toString());
				sb.append("=");
				sb.append(entry.getValue().toString());
				counter--;
				if(counter != 0) {
					sb.append(", ");
				} else {
					break forLoop;
				}
				entry = entry.next;
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Deletes all entries from the hashtable.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
			modificationCount++;
		}
	}
	
	/**
	 * Creates an iterator.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Iterator implementation used for iterating through the hashtable.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Most recently returned entry.
		 */
		private TableEntry<K, V> current = null;
		
		/**
		 * Next entry to be returned.
		 */
		private TableEntry<K, V> next = null;
		
		/**
		 * Slot the iterator is currently positioned at.
		 */
		private int slot = 0;
		
		/**
		 * true if the iterator still has entries to return
		 */
		private boolean alive = true;
		
		/**
		 * true if the method remove can be called
		 */
		private boolean removable = false;
		
		/**
		 * Number of modifications that is expected
		 */
		private int expectedModificationCount = SimpleHashtable.this.modificationCount;
		
		/**
		 * Constructor
		 */
		public IteratorImpl() {
			next = table[0];
			while(slot < table.length-1 && next == null) {
				slot++;
				next = table[slot];
			}
			if(next == null) {
				alive = false;
			}
		}
		
		/**
		 * Checks if the iterator still has entries to return.
		 * 
		 * @return <code>true</code> if there are more entries, <code>false</code> otherwise.
		 */
		public boolean hasNext() {
			if(SimpleHashtable.this.modificationCount != expectedModificationCount) {
				throw new ConcurrentModificationException("The collection has been modified from outside "
						+ "while iterating.");
			}
			
			return alive;
		}
		
		/**
		 * Return the next entry
		 */
		public TableEntry<K, V> next() {
			if(!alive) {
				throw new NoSuchElementException("There are no more elements in the map");
			}
			
			if(SimpleHashtable.this.modificationCount != expectedModificationCount) {
				throw new ConcurrentModificationException("The collection has been modified from outside "
						+ "while iterating.");
			}
			
			current = next;
			
			next = next.next;
			while(slot < table.length-1 && next == null) {
				slot++;
				next = table[slot];
			}
			if(next == null) {
				alive = false;
			}
			
			removable = true;
			
			return current;
		}
		
		/**
		 * Removes the most recently returned entry from the hashtable.
		 */
		public void remove() {
			if(SimpleHashtable.this.modificationCount != expectedModificationCount) {
				throw new ConcurrentModificationException("The collection has been modified from outside "
						+ "while iterating.");
			}
			
			if(!removable) {
				throw new IllegalStateException("Nothing to be removed.");
			}
			
			removable = false;
			SimpleHashtable.this.remove(current.getKey());
			expectedModificationCount++;
		}
	}
}
