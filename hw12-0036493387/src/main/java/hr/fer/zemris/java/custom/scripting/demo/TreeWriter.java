package hr.fer.zemris.java.custom.scripting.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * This program takes a path to one SmartScript as an argument, parses it, and then recreates
 * it by traversing all of its nodes.
 * 
 * @author Mihael Stočko
 *
 */
public class TreeWriter {

	/**
	 * This is a visitor that traverses all nodes of a SmartScript document.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	public static class WriterVisitor implements INodeVisitor {
		/**
		 * String builder used for creating an output String.
		 */
		StringBuilder sb = new StringBuilder();
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0, numOfChildren = node.numberOfChildren(); i < numOfChildren; i++) {
				node.getChild(i).accept(this);
			}
			
			System.out.println(sb.toString());
			sb.setLength(0);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			sb.append("{$=");
			Element[] elems = node.getElements();
			for(int i = 0, numOfElems = elems.length; i < numOfElems-1; i++) {
				appendElement(sb, elems[i]);
				sb.append(" ");
			}
			appendElement(sb, elems[elems.length-1]);
			sb.append("$}");
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			sb.append("{$FOR ");
			appendElement(sb, ((ForLoopNode)node).getVariable());
			sb.append(" ");
			appendElement(sb, ((ForLoopNode)node).getStartExpression());
			sb.append(" ");
			appendElement(sb, ((ForLoopNode)node).getEndExpression());
			if(((ForLoopNode)node).getStepExpression() != null) {
				sb.append(" ");
				appendElement(sb, ((ForLoopNode)node).getStepExpression());
			}
			sb.append("$}");
			
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			
			sb.append("{$END$}");
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			String s = node.getText();
			for(int i = 0, size = s.length(); i < size; i++) {
				if(s.charAt(i) == '\\' || s.charAt(i) == '{') {
					sb.append('\\');
				} 
				sb.append(s.charAt(i));
			}
		}
		
		/**
		 * An auxiliary method used for appending various types of characters to the StringBuilder.
		 * 
		 * @param sb StringBuilder
		 * @param element Element to be appended.
		 */
		private static void appendElement(StringBuilder sb, Element element) {
			if(element instanceof ElementConstantInteger || element instanceof ElementConstantDouble ||
					element instanceof ElementOperator || element instanceof ElementVariable) {
				sb.append(element.asText());
			} else if(element instanceof ElementFunction) {
				sb.append("@");
				sb.append(element.asText());
			} else {
				sb.append("\"");
				String s = element.asText();
				for(int j = 0, size = s.length(); j < size; j++) {
					if(s.charAt(j) == '\\' || s.charAt(j) == '"') {
						sb.append('\\');
					} 
					sb.append(s.charAt(j));
				}
				sb.append("\"");
			}
		}
	}
	
	/**
	 * Reads from the given document and turns it into a String.
	 * 
	 * @param filename Name of the document.
	 * @return The document turned to string.
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is =
		this.getClass().getClassLoader().getResourceAsStream(filename)) {
		byte[] buffer = new byte[1024];
		while(true) {
		int read = is.read(buffer);
		if(read<1) break;
		bos.write(buffer, 0, read);
		}
		return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
		return null;
		}
	}
	
	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		String filepath = null;
		if(args.length != 1) {
			System.out.println("Invalid program call.");
			System.exit(1);
		} else {
			filepath = args[0];
		}
		
		String docBody = null;
		TreeWriter sst = new TreeWriter();
		try {
			docBody = sst.loader(filepath);
		} catch(Exception e) {
			System.out.println("Invalid filepath.");
			System.exit(1);
		}
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse the document!");
			return;
		} 
		
		DocumentNode document = parser.getDocumentNode();
		document.accept(new WriterVisitor());
	}
}
