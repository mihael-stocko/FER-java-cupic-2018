package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class demonstrates how request context creates file headers.
 * 
 * @author Mihael Stočko
 *
 */
public class RequestContextDemo {

	/**
	 * Main method.
	 * 
	 * @param args Not used
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		demo1("src/main/resources/primjer1.txt", "ISO-8859-2");
		demo1("src/main/resources/primjer2.txt", "UTF-8");
		demo2("src/main/resources/primjer3.txt", "UTF-8");
	}

	/**
	 * A simpe example
	 * 
	 * @throws IOException
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
	/**
	 * A slightly more complex example
	 * 
	 * @throws IOException
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/", false));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", false));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}