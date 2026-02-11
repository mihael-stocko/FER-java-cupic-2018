package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class represents the context for turtle's states.
 * 
 * @author Mihael Stočko
 *
 */
public class Context {

	/**
	 * Internally used stack
	 */
	private ObjectStack stack;
	
	/**
	 * Constructor
	 */
	public Context() {
		stack = new ObjectStack();
	}

	/**
	 * Gets a state from the context and returns it
	 * @return Current state of the turtle
	 */
	public TurtleState getCurrentState() {
		if(stack.isEmpty()) {
			throw new UnsupportedOperationException("There are no states on the stack.");
		}
		
		return (TurtleState)stack.peek();
	}
	
	/**
	 * Pushes a state onto the context
	 * @param state
	 */
	public void pushState(TurtleState state) {
		if(state == null) {
			throw new NullPointerException("Null is not a legal turtle state.");
		}
		
		stack.push(state);
	}
	
	/**
	 * Pops a state from the top of the context
	 */
	public void popState() {
		if(stack.isEmpty()) {
			throw new UnsupportedOperationException("There are no states on the stack.");
		}
		
		stack.pop();
	}
}
