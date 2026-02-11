package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of the Collection class that uses a linked list as an auxiliary structure.
 * Provides support for basic array indexed collection operations.
 * 
 * @author Mihael Stočko
 * 
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * A structure representing a node in the linked list.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private static class ListNode {
		/**
		 * Value of the node.
		 */
		public Object value;
		
		/**
		 * Reference to the next node.
		 */
		public ListNode next;
		
		/**
		 * Reference to the previous node.
		 */
		public ListNode previous;
	}
	
	/**
	 * Size of the collection.
	 */
	private int size;
	
	/**
	 * Reference to the first node.
	 */
	private ListNode first;
	
	/**
	 * Reference to the last node.
	 */
	private ListNode last;
	
	/**
	 * Default constructor.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Constructor. Adds all of the elements from the provided collection to the collection being created.
	 * 
	 * @param other Collection being used as a source.
	 * @throws NullPointerException
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other == null) {
			throw new NullPointerException("The argument cannot be null.");
		}
		
		addAll(other);
	}
	
	/**
	 * Adds an elements to the collection.
	 * 
	 * @param value Object to be added.
	 * @throws NullPointerException
	 */
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("The argument cannot be null.");
		}
		
		ListNode temp = new ListNode();
		temp.value = value;
		
		if(first == null) {
			first = temp;
			last = temp;
			temp.next = null;
			temp.previous = null;
			size++;
		} else {
			temp.previous = last;
			temp.next = null;
			last.next = temp;
			last = temp;
			size++;
		}
	}
	
	/**
	 * Method that returns a reference to the indexed node.
	 * 
	 * @param index Index of the requested node.
	 * @return Reference to the requested node.
	 */
	private ListNode find(int index) {
		ListNode temp;
		
		if(index < size/2) {
			temp = first;
			
			for(int i = 0; i < index; ++i) {
				temp = temp.next;
			}
		} else {
			temp = last;
			
			for(int i = 0; i < size-index-1; ++i) {
				temp = temp.previous;
			}
		}
		
		return temp;
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
			throw new IndexOutOfBoundsException("Index must be between 0 and size-1, was " + size + ".");
		}
		
		return find(index).value;
	}	
	
	/**
	 * Deletes all elements from the collection.
	 */
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Inserts an object into the collection at the specific position.
	 * 
	 * @param value Object to be inserted.
	 * @param position Index at which the element will be inserted.
	 * @throws IndexOutOfBoundsException, NullPointerException
	 */
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException("null cannot be added to the collection.");
		}
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Index must be between 0 and size, was " + size + ".");
		}
		
		if(position == size || size == 0) {
			add(value);
			return;
		}
		
		ListNode newNode = new ListNode();
		newNode.value = value;
		
		if(position == 0) {
			newNode.next = first;
			newNode.previous = null;
			first.previous = newNode;
			first = newNode;
			size++;
			return;
		}
		
		ListNode temp = find(position);
		newNode.next = temp;
		newNode.previous = temp.previous;
		temp.previous.next = newNode;
		temp.previous = newNode;
		size++;
	}
	
	/**
	 * Returns the index of the passed object.
	 * 
	 * @param value Object of which the index is requested.
	 * @return Index of the passed object if the collection contains the object, <code>-1</code> otherwise.
	 */
	public int indexOf(Object value) {
		ListNode temp = first;
		for(int i = 0; i < size; ++i) {
			if(temp.value.equals(value)) {
				return i;
			}
			temp = temp.next;
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
			throw new IllegalArgumentException("Index must be between 0 and size-1, was " + size + ".");
		}
		
		if(size == 1) {
			first = null;
			last = null;
			size = 0;
			return;
		}
		
		ListNode temp = find(index);
		temp.next.previous = temp.previous;
		temp.previous.next = temp.next;
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
		ListNode temp = first;
		
		for(int i = 0; i < size; ++i) {
			if(temp.value.equals(value)) {
				return true;
			}
			temp = temp.next;
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
	
		ListNode temp = first;
		for(int i = 0; i < size; ++i) {
			processor.process(temp.value);
			temp = temp.next;
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
		
		ListNode temp = first;
		for(int i = 0; i < size; ++i) {
			newArray[i] = temp.value;
			temp = temp.next;
		}
		
		return newArray;
	}
}
