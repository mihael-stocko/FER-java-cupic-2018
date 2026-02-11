package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of the stack data structure.
 * 
 * @author Mihael Stočko
 *
 */

public class ObjectStack {
	
	/**
	 * Internally used collection for storing the elements.
	 */
	private ArrayIndexedCollection arrayCollection;
	
	/**
	 * Default constructor.
	 */
	public ObjectStack() {
		arrayCollection = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if the collection is empty.
	 * 
	 * @return <code>true</code> if the collection is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return arrayCollection.isEmpty();
	}
	
	/**
	 * Returns the current size of the collection.
	 * 
	 * @return Size of the collection.
	 */
	public int size() {
		return arrayCollection.size();
	}
	
	/**
	 * Deletes all elements from the collection.
	 */
	public void clear() {
		arrayCollection.clear();
	}
	
	/**
	 * Pushes the provided object onto the stack. Does not accept null references.
	 * 
	 * @param value Object to be pushed onto the stack.
	 * @throws NullPointerException
	 */
	public void push(Object value) {
		if(value == null) {
			throw new NullPointerException("null cannot be pushed onto the stack.");
		}
		
		arrayCollection.add(value);
	}
	
	/**
	 * Pops the element from the top of the stack.
	 * 
	 * @return Popped object.
	 * @throws EmptyStackException
	 */
	public Object pop() {
		if(this.size() == 0) {
			throw new EmptyStackException("Cannot pop an element from an empty stack.");
		}
		Object object = peek();
		arrayCollection.remove(arrayCollection.size()-1);
		
		return object;
	}
	
	/**
	 * Returns the element from the top of the stack. Does not remove it.
	 * 
	 * @return Object at the top of the stack.
	 * @throws EmptyStackException
	 */
	public Object peek() {
		if(arrayCollection.size() == 0) {
			throw new EmptyStackException("The stack is empty.");
		}
		
		return arrayCollection.get(arrayCollection.size()-1);
	}
}
