package hr.fer.zemris.java.custom.collections;

/**
 * A template for a basic collection. Implementation is to be given by the classes that extend this class.
 * 
 * @author Mihael Stočko
 *
 */

public class Collection {

	/**
	 * Default constructor.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Checks if the collection is empty.
	 * 
	 * @return <code>true</code> if the collection is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the current size of the collection. Here implemented to return 0.
	 * 
	 * @return <code>0</code>
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds an elements to the collection. Here implemented to do nothing.
	 * 
	 * @param value Object to be added.
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if the collection contains an element. Here implemented to return false.
	 * 
	 * @param value Object which is being checked.
	 * @return <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes the elements at the given index. Here implemented to return false.
	 * 
	 * @param index Index of the element to be removed.
	 * @return <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Returns the whole collection as an array. Here implemented to throw an UnsupportedOperationException
	 * 
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Takes in a Processor object as input and applies its process method on every element of the collection.
	 * Here implemented to do nothing.
	 * 
	 * @param processor Processor object
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds all of the elements from the provided collection to this collection.
	 * 
	 * @param other Source collection
	 * @throws NullPointerException
	 */
	public void addAll(Collection other) {
		
		if(other == null) {
			throw new NullPointerException("null cannot be used as an argument.");
		}
		
		Processor processor = new Processor() {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		};
		
		other.forEach(processor);
	}
	
	/**
	 * Deletes all elements from the collection. Here implemented to do nothing.
	 */
	public void clear() {
		
	}
}
