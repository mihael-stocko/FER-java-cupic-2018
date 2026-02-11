package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is a collection that maps one String to a stack. Values can be pushed onto or popped from
 * a specific stack mapped by the key in question.
 * 
 * @author Mihael Stočko
 *
 */
public class ObjectMultistack {

	/**
	 * This class models one entry of the stack. It has a reference to the next entry, and so the
	 * stacks can be formed.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private static class MultistackEntry {
		
		/**
		 * A ValueWrapper that is being held by this entry.
		 */
		public ValueWrapper wrapper;

		/**
		 * A reference to the next entry.
		 */
		public MultistackEntry next = null;
		
		/**
		 * Constructor. Accepts a ValueWrapper which cannot be null.
		 * 
		 * @param wrapper
		 * @throws NullPointerException
		 */
		public MultistackEntry(ValueWrapper wrapper) {
			Objects.requireNonNull(wrapper, "A multistack entry cannot contain null.");
			this.wrapper = wrapper;
		}
	}
	
	/**
	 * A map used for mapping Strings to stacks.
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();
	
	/**
	 * Pushes the given ValueWrapper onto the stack mapped by the given String. None of the given
	 * arguments can be null.
	 * 
	 * @param name
	 * @param valueWrapper
	 * @throws NullPointerException
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		Objects.requireNonNull(name, "Cannot push an object with null as a key onto the multistack.");
		Objects.requireNonNull(valueWrapper, "Cannot push null onto the multistack.");
		
		MultistackEntry entry = new MultistackEntry(valueWrapper);
		if(map.containsKey(name)) {
			entry.next = map.get(name);
		}
		map.put(name, entry);
	}
	
	/**
	 * Pops a ValueWrapper from the stack mapped by the given String. The String cannot be null.
	 * 
	 * @param name
	 * @return ValueWrapper from the stack. 
	 * @throws NullPointerException
	 */
	public ValueWrapper pop(String name) {
		Objects.requireNonNull(name, "Pop does not accept null as an argument.");
		if(!map.containsKey(name)) {
			throw new IllegalArgumentException("There is nothing on the stack under the given key.");
		}
		
		MultistackEntry entry = map.get(name);
		map.put(name, entry.next);
		
		if(entry.next == null) {
			map.remove(name);
		}
		
		return entry.wrapper;
	}
	
	/**
	 * Returns a ValueWrapper from the top of the stack mapped by the given String. 
	 * The String cannot be null.
	 * 
	 * @param name
	 * @return ValueWrapper from the stack. 
	 * @throws NullPointerException
	 */
	public ValueWrapper peek(String name) {
		Objects.requireNonNull(name, "Peek does not accept null as an argument.");
		if(!map.containsKey(name)) {
			throw new IllegalArgumentException("There is nothing on the stack under the given key.");
		}
		
		MultistackEntry entry = map.get(name);
		return entry.wrapper;
	}
	
	/**
	 * Checks if the stack mapped by the given String is empty.
	 * @param name 
	 * @return <code>true</code> if the stack is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty(String name) {
		return !map.containsKey(name);
	}
}
