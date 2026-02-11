package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This interface defines methods that must be implemented by a visitor that traverses
 * a SmartScript document tree.
 * 
 * @author Gbuljba
 *
 */
public interface INodeVisitor {
	/**
	 * Visits a text node.
	 * 
	 * @param node Node that is being visited.
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Visits a for loop node.
	 * 
	 * @param node Node that is being visited.
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Visits an echo node.
	 * 
	 * @param node Node that is being visited.
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Visits a document node.
	 * 
	 * @param node Node that is being visited.
	 */
	public void visitDocumentNode(DocumentNode node);
}
