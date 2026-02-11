package hr.fer.zemris.java.hw03;

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
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * This program is used for testing the SmartScriptParser. It takes one argument from the command line:
 * a filepath to the document to be parsed. It then parses the given document, recreates it from the tree,
 * parses it again and recreates the String one more time. The String is then printed out. 
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptTester {
	/**
	 * Main method
	 * 
	 * @param args filepath to the document to be parsed. ( example: document1.txt  or  "document1.txt" )
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
		SmartScriptTester sst = new SmartScriptTester();
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
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody2);
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
	 * This method recreates the original String from a parsed tree.
	 * 
	 * @param document Root of the tree.
	 * @return Recreated String.
	 */
	public static String createOriginalDocumentBody(Node document) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0, max = document.numberOfChildren(); i < max; i++) {
			Node child = document.getChild(i);
			
			if(child instanceof TextNode) {
				String s = ((TextNode)child).getText();
				for(int j = 0, size = s.length(); j < size; j++) {
					if(s.charAt(j) == '\\' || s.charAt(j) == '{') {
						sb.append('\\');
					} 
					sb.append(s.charAt(j));
				}
			} else if(child instanceof EchoNode) {
				sb.append("{$=");
				Element[] elems = ((EchoNode)child).getElements();
				for(int j = 0, numOfElems = elems.length; j < numOfElems-1; j++) {
					appendElement(sb, elems[j]);
					sb.append(" ");
				}
				appendElement(sb, elems[elems.length-1]);
				sb.append("$}");
			
			} else if(child instanceof ForLoopNode) {
				sb.append("{$FOR ");
				appendElement(sb, ((ForLoopNode)child).getVariable());
				sb.append(" ");
				appendElement(sb, ((ForLoopNode)child).getStartExpression());
				sb.append(" ");
				appendElement(sb, ((ForLoopNode)child).getEndExpression());
				if(((ForLoopNode)child).getStepExpression() != null) {
					sb.append(" ");
					appendElement(sb, ((ForLoopNode)child).getStepExpression());
				}
				sb.append("$}");
				sb.append(createOriginalDocumentBody(child));
				sb.append("{$END$}");
			}
		}
		
		return sb.toString();
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
