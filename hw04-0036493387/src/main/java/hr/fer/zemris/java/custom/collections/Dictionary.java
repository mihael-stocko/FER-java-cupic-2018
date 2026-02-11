package hr.fer.zemris.java.custom.collections;

/**
 * A simple dictionary. Values are remembered under specified keys.
 * 
 * @author Mihael Stočko
 *
 */
public class Dictionary {

	/**
	 * Internally used collection.
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * A class that models a dictionary entry.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private static class Entry {
		
		/**
		 * Key of an entry
		 */
		private Object key;
		
		/**
		 * Value of an entry
		 */
		private Object value;
		
		/**
		 * Constructor
		 * 
		 * @param key Key of an entry
		 * @param value Value of an entry
		 * @throws NullPointerException
		 */
		public Entry(Object key, Object value) {
			if(key == null) {
				throw new NullPointerException("Key cannot be null.");
			}
			
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Getter for key
		 * 
		 * @return Key of an entry
		 */
		public Object getKey() {
			return key;
		}
		
		/**
		 * Getter for value
		 * 
		 * @return Value of an entry
		 */
		public Object getValue() {
			return value;
		}
	}
	
	/**
	 * Constructor.
	 */
	public Dictionary() {
		collection = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if the dictionary is empty.
	 * 
	 * @return <code>true</code> if the dictionary is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns the size of the dictionary.
	 * 
	 * @return size of the dictionary
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Clears the content of the dictionary.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Puts an object into the dictionary under a specified key.
	 * 
	 * @param key Key for the entry
	 * @param value Value of the object
	 * @throws NullPointerException
	 */
	public void put(Object key, Object value) {
		if(key == null) {
			throw new NullPointerException("Key cannot be null.");
		}
		
		Object item = find(key);
		if(item == null) {
			collection.add(new Entry(key, value));
		} else {
			int index = collection.indexOf(item);
			collection.remove(index);
			collection.insert(new Entry(key, value), index);
		}
	}
	
	/**
	 * Gets the object associated with the provided key.
	 * 
	 * @param key Key of the requested object
	 * @return Value of the object found
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Object get(Object key) {
		if(key == null) {
			throw new NullPointerException("Key cannot be null.");
		}
		
		Object item = find(key);
		if(item == null) {
			throw new IllegalArgumentException("There is no such key in the dictionary, was " + key.toString());
		}
		
		return ((Entry)item).getValue();
	}
	
	/**
	 * Finds the object associated with the provided key.
	 * 
	 * @param key Key of the requested object
	 * @return Value of the object found, <code>null</code> if the object in not present.
	 */
	private Object find(Object key) {
		for(int i = 0, size = collection.size(); i < size; i++) {
			if(((Entry)collection.get(i)).getKey().equals(key)) {
				return collection.get(i);
			}
		}
		return null;
	}
}
