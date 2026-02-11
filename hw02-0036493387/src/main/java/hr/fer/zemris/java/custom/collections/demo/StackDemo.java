package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * A program that demonstrates the usage of the stack.
 * Takes one string as an input, parses it and extracts operands and operators of an expression from it.
 * Supported operations are summation, subtraction, multiplication, integer division and modulo.
 * 
 * @author Mihael Stočko
 *
 */
public class StackDemo {
	
	/**
	 * Main method.
	 * 
	 * @param args String that defines an expression.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Wrong input format.");
			return;
		}
		String[] arguments = args[0].split("\\s+");
		ObjectStack stack = new ObjectStack();
		
		boolean error = false;
		
		for(String s : arguments) {
			try {
				int x = Integer.parseInt(s);
				stack.push(x);
			} catch(Exception x) {
				int x1;
				int x2;
				
				try {
					x2 = (Integer)stack.pop();
					x1 = (Integer)stack.pop();
				} catch(Exception e) {
					System.out.println("Wrong input format.");
					error = true;
					break;
				}
				
				if(s.equals("+")) {
					stack.push(x1 + x2);
				} else if(s.equals("-")) {
					stack.push(x1 - x2);
				} else if(s.equals("*")) {
					stack.push(x1 * x2);
				} else if(s.equals("/")) {
					if(x2 == 0) {
						System.out.println("Cannot divide by zero.");
						error = true;
						break;
					}
					stack.push(x1 / x2);
				} else if(s.equals("%")) {
					stack.push(x1 % x2);
				} else {
					System.out.println("Unsupported operator " + s);
					error = true;
					break;
				}
			}
		}
		
		if(!error) {
			if(stack.size() != 1) {
				System.out.println("Wrong input format.");
			} else {
				System.out.println("Expression evaluates to " + stack.pop() + ".");
			}
		}
	}
}
