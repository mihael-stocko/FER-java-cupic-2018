package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of the Collection class that uses an array as an auxiliary structure.
 * Provides support for basic array indexed collection operations.
 * 
 * @author Mihael Stočko
 * 
 */
public class ArrayIndexedCollection extends Collection {
	
	/**
	 * Number of elements in the collection.
	 */
	private int size;
	
	/**
	 * Current capacity of the collection.
	 */
	private int capacity;
	
	/**
	 * Array used for element storage.
	 */
	private Object[] elements;
	
	/**
	 * Default constructor
	 */
	public ArrayIndexedCollection() {
		this(Constants.DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor. Capacity of the collection is set to the passed parameter.
	 * 
	 * @param initialCapacity Initial capacity of the collection.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Constructor argument cannot be less than 1, was " + initialCapacity + ".");
		}
		
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}
	
	/**
	 * Constructor. Uses another collection as the source of elements.
	 * 
	 * @param other Collection used for the creation.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, Constants.DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor. Uses another collection as the source of elements.
	 * Sets the initial capacity to the passed argument if it is greater than
	 * the size of the passed collection. Otherwise sets the initial capacity to
	 * the size of the passed collection.
	 * 
	 * @param other
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) {
			throw new NullPointerException("Collection used as the argument cannot be null.");
		}
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Constructor argument cannot be less than 1, was " + initialCapacity + ".");
		}
		
		if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
		
		this.addAll(other);
	}
	
	/**
	 * Adds an elements to the collection.
	 * 
	 * @param value Object to be added.
	 * @throws NullPointerException
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("null cannot be added to the collection.");
		}
		
		if(size == capacity) {
			resize();
		}
		
		elements[size] = value;
		size++;
	}
	
	/**
	 * Returns the elements at the given index.
	 * 
	 * @param index Index of the requested element.
	 * @return Requested object
	 * @throws IndexOutOfBoundsException
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index must be between 0 and size-1, was " + index);
		}
		
		return elements[index];
	}
	
	/**
	 * Deletes all elements from the collection.
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; ++i) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Doubles the capacity of the collection.
	 */
	private void resize() {
		capacity *= Constants.RESIZE_FACTOR;
		Object[] oldElements = elements;
		elements = new Object[capacity];
		for(int i = 0; i < size; ++i) {
			elements[i] = oldElements[i];
		}
	}
	
	/**
	 * Inserts an object into the collection at the specific position.
	 * 
	 * @param value Object to be inserted.
	 * @param position Index at which the element will be inserted.
	 * @throws IndexOutOfBoundsException, NullPointerException
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Position must be between 0 and size, was " + size + ".");
		}
		
		if(value == null) {
			throw new NullPointerException("null cannot be added to the collection.");
		}
		
		if(size == capacity) {
			resize();
		}
		
		Object temp1 = elements[position];
		Object temp2;
		for(int i = position; i < size; ++i) {
			temp2 = elements[i+1];
			elements[i+1] = temp1;
			temp1 = temp2;
		}
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Returns the index of the passed object.
	 * 
	 * @param value Object of which the index is requested.
	 * @return Index of the passed object if the collection contains the object, <code>-1</code> otherwise.
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		
		for(int i = 0; i < size; ++i) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Removes the elements at the given index.
	 * 
	 * @param index Index of the element to be removed.
	 * @throws IndexOutOfBoundsException
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index must be between 0 and size-1, was " + index + ".");
		}
		
		for(int i = index; i < size; ++i) {
			elements[i] = elements[i+1];
		}
		
		size--;
	}
	
	/**
	 * Returns the current size of the collection.
	 * 
	 * @return Size of the collection.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Checks if the collection contains an element.
	 * 
	 * @param value Object which is being checked.
	 * @return <code>true</code> if the object is contained in the collection, <code>false</code> otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < size; ++i) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Takes in a Processor object as input and applies its process method on every element of the collection.
	 * 
	 * @param processor Processor object
	 * @throws NullPointerException
	 */
	@Override
	public void forEach(Processor processor) {
		if(processor == null) {
			throw new NullPointerException("Processor cannot be null.");
		}
		
		for(int i = 0; i < size; ++i) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Returns the whole collection as an array.
	 * 
	 * @return Collection as an array.
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[size];
		
		for(int i = 0; i < size; ++i) {
			newArray[i] = elements[i];
		}
		
		return newArray;
	}
	
	/**
	 * Getter for capacity.
	 * 
	 * @return capacity
	 */
	public int getCapactiy() {
		return capacity;
	}
}
