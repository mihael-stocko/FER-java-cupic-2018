package hr.fer.zemris.java.custom.scripting.exec;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This program demonstrates the SmartScriptEngine.
 * 
 * @author Mihael Stočko
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Main method. Shows how the engine processes various types of SmartScripts.
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
		test5();
	}
	
	/**
	 * Example number 1
	 */
	private static void test1() {
		String documentBody = readFromDisk("osnovni.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
	
	/**
	 * Example number 2
	 */
	private static void test2() {
		String documentBody = readFromDisk("zbrajanje.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
	
	/**
	 * Example number 3
	 */
	private static void test3() {
		String documentBody = readFromDisk("brojPoziva.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
		cookies);
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(), rc
		).execute();
		System.out.println();
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
	}
	
	/**
	 * Example number 4
	 */
	private static void test4() {
		String documentBody = readFromDisk("fibonacci.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
	
	/**
	 * Example number 5
	 */
	private static void test5() {
		String documentBody = readFromDisk("fibonaccih.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		try(FileOutputStream fos = new FileOutputStream("src/main/resources/file.html")) {
			new SmartScriptEngine(
					new SmartScriptParser(documentBody).getDocumentNode(),
					new RequestContext(fos, parameters, persistentParameters, cookies)
					).execute();
		} catch(Exception ignoragble) {
			
		}	
	}
	
	/**
	 * Used for reading the whole file from the disk.
	 * 
	 * @param filepath Path to the script
	 */
	private static String readFromDisk(String filepath) {
		String documentBody = null;
		SmartScriptEngineDemo sst = new SmartScriptEngineDemo();
		try {
			documentBody = sst.loader(filepath);
		} catch(Exception e) {
			System.out.println("Invalid filepath.");
			System.exit(1);
		}
		
		return documentBody;
	}
	
	/**
	 * Auxiliary method for reading the file.
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
}
