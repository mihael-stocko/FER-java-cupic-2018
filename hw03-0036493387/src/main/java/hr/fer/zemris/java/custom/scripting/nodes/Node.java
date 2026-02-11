package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.*;

/**
 * A representation of a node.
 * 
 * @author Mihael Stočko
 *
 */
public class Node {
	
	/**
	 * Collection of children.
	 */
	private ArrayIndexedCollection children;
	
	/**
	 * Has the array of children been created. This is used for creating the array on demand.
	 */
	private boolean arrayCreated = false;
	
	/**
	 * Adds a child to the collection of children.
	 * 
	 * @param child Child to be added.
	 */
	public void addChildNode(Node child) {
		if(!arrayCreated) {
			children = new ArrayIndexedCollection();
			arrayCreated = true;
		}
		
		children.add(child);
	}
	
	/**
	 * Returns the number of children currently stored in the node.
	 * 
	 * @return Number of children.
	 */
	public int numberOfChildren() {
		if(children != null) {
			return children.size();
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns the child at the given index.
	 * 
	 * @param index Index of the child that is being requested.
	 * @return Child at index.
	 */
	public Node getChild(int index) {
		if(children == null) {
			throw new UnsupportedOperationException("The node contains no elements.");
		} else {
			try {
				return (Node)children.get(index);
			} catch(Exception e) {
				throw new IndexOutOfBoundsException("Index too great or to small, was " + index + ".");
			}
		}
	}
	
	
}
