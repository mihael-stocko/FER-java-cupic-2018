package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A representation of an echo node.
 * 
 * @author Mihael Stočko
 *
 */
public class EchoNode extends Node {
	
	/**
	 * Internal collection of elements.
	 */
	private Element[] elements;

	/**
	 * Constructor. Takes in an array of elements.
	 * 
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		super();
		if(elements == null) {
			elements = new Element[0];
		}
		this.elements = elements;
	}

	/**
	 * Getter for elements.
	 * 
	 * @return Array of elements stored internally.
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Calls the given INodeVisitor's visitEchoNode method with itself as an argument.
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
