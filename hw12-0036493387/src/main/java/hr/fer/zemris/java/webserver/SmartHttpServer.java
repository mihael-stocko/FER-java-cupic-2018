package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A simple web server. It listens for client requests, processes them, and returns the responses.
 * It can execute SmartScripts.
 * 
 * @author Mihael Stočko
 *
 */
public class SmartHttpServer {
	/**
	 * This class holds information about a single session.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session identifier
		 */
		private String sid;
		
		/**
		 * Host of the session
		 */
		private String host;
		
		/**
		 * Moment when the session will expire.
		 */
		private long validUntil;
		
		/**
		 * Map of permanent parameters for this session.
		 */
		private Map<String, String> map = new ConcurrentHashMap<>();
		
		/**
		 * Constructor.
		 */
		public SessionMapEntry(String sid, String host, long validUntil) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
		}
		
		/**
		 * Getter for host
		 */
		public String getHost() {
			return host;
		}

		/**
		 * Getter for validUntil
		 */
		public long getValidUntil() {
			return validUntil;
		}

		/**
		 * Setter for validUntil
		 */
		public void setValidUntil(long validUntil) {
			this.validUntil = validUntil;
		}

		/**
		 * Getter for parameters map
		 */
		public Map<String, String> getMap() {
			return map;
		}
	}
	
	/**
	 * Length of the session identifier
	 */
	public static final int sidLength = 20;
	
	/**
	 * This indicates how long will the auxiliary thread wait before it removes
	 * all expired sessions from the list of sessions.
	 */
	public static final int expireTime = 60;
	
	/**
	 * Address
	 */
	private String address;
	
	/**
	 * Domain name
	 */
	private String domainName;
	
	/**
	 * Port
	 */
	private int port;
	
	/**
	 * Number of worker threads
	 */
	private int workerThreads;
	
	/**
	 * Time that indicates how long a session lasts
	 */
	private int sessionTimeout;
	
	/**
	 * Document root
	 */
	private Path documentRoot;
	
	/**
	 * Map of mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * The main server thread
	 */
	private ServerThread serverThread;
	
	/**
	 * Thread pool for threads processing requests
	 */
	private ExecutorService threadPool;
	
	/**
	 * A map of IWebWorkers
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * Map of sessions. Each session is mapped to a session identifier
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**
	 * Used for creating a random sid
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * This thread removes expired sessions from the map of sessions.
	 */
	private Thread deadSessions = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(1000 * expireTime);
			} catch(InterruptedException e) {}
			sessions.forEach((k, v) -> {
				if(v.validUntil < System.currentTimeMillis()/1000) {
					sessions.remove(k);
				}
			});
		}
	});
	
	/**
	 * Constructor.
	 * 
	 * @param configFileName A path to the .properties file with configuration info.
	 */
	public SmartHttpServer(String configFileName) {
		Properties prop = new Properties();
		try(InputStream is = new FileInputStream(configFileName)) {
			prop.load(is);
			address = prop.getProperty("server.address");
			domainName = prop.getProperty("server.domainName");
			port = Integer.parseInt(prop.getProperty("server.port"));
			workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
			documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
			
			String mimePath = prop.getProperty("server.mimeConfig");
			InputStream is2 = new FileInputStream(mimePath);
			Properties mimes = new Properties();
			mimes.load(is2);
			Set<Object> keys = mimes.keySet();
			for(Object key : keys) {
				mimeTypes.put((String)key, mimes.getProperty((String)key));
			}
			is2.close();
			
			String workersPath = prop.getProperty("server.workers");
			InputStream is3 = new FileInputStream(workersPath);
			Properties workers = new Properties();
			workers.load(is3);
			Set<Object> workerKeys = workers.keySet();
			if(workerKeys.size() != workers.size()) {
				throw new IllegalArgumentException("workers.properties file cannot contain two same keys.");
			}
			for(Object key : workerKeys) {
				String wPath = ((String)key);
				String fqcn = workers.getProperty(wPath);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put(wPath.substring(1), iww);
			}
			is2.close();
		} catch(Exception e) {
			throw new IllegalStateException("Cannot create a SmartHTTPServer object. " + e.getMessage());
		}
		serverThread = new ServerThread();
	}
	
	/**
	 * Starts the server.
	 */
	protected synchronized void start() {
		serverThread.start();
		deadSessions.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}
		
	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.stop();
		deadSessions.stop();
		threadPool.shutdown();
	}
		
	/**
	 * Main thread that listens for requests and delegates them to threads in the thread pool.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	protected class ServerThread extends Thread {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {	
			try(ServerSocket serverSocket = new ServerSocket(port)) {
				while(true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch(IOException e) {
				throw new IllegalStateException(e.getMessage());
			}
		}
	}

	/**
	 * This thread serves a request.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Description for error 400
		 */
		private static final String error400 = "Bad request";
		
		/**
		 * Description for error 403
		 */
		private static final String error403 = "Forbidden";
		
		/**
		 * Description for error 404
		 */
		private static final String error404 = "Not found";
		
		/**
		 * Default mime type
		 */
		private static final String defaultMime = "application/octet-stream";
		
		/**
		 * Path prefix for workers
		 */
		private static final String workerPrefix = "ext\\";
		
		/**
		 * Path prefix for private files
		 */
		private static final String privateFolder = "private\\";
		
		/**
		 * Extension of SmartScripts
		 */
		private static final String scriptExtension = "smscr";
		
		/**
		 * Socket for communicating with the client
		 */
		private Socket csocket;
		
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		
		/**
		 * Output stream
		 */
		private OutputStream ostream;
		
		/**
		 * HTTP Version
		 */
		private String version;
		
		/**
		 * Method of the request
		 */
		private String method;
		
		/**
		 * Host
		 */
		private String host;
		
		/**
		 * Request parameters
		 */
		private Map<String,String> params = new HashMap<String, String>();
		
		/**
		 * Temporary request parameters
		 */
		private Map<String,String> tempParams = new HashMap<String, String>();
		
		/**
		 * Persistent request parameters
		 */
		private Map<String,String> permParams = new HashMap<String, String>();
		
		/**
		 * List of cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * Session identifier
		 */
		private String SID;
		
		/**
		 * Request context for the request
		 */
		private RequestContext context;
		
		/**
		 * Constructor
		 * 
		 * @param csocket Socket of the client
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
			
		/**
		 * Getter for {@link RequestContext}
		 */
		private RequestContext getRequestContext() {
			if(context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
			}
			
			return context;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			
			try{
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
				byte[] request = readRequest(istream);
				if(request==null) {
					sendError(ostream, 400, error400);
					return;
				}
				String requestStr = new String(
					request, 
					StandardCharsets.US_ASCII
				);
				
				List<String> lines = extractHeaders(requestStr);
	
				if(lines.size() < 1) {
					sendError(ostream, 400, error400);
					return;
				}
				
				String firstLine = lines.get(0);
				String[] firstLineArgs = firstLine.split(" ");
				method = firstLineArgs[0];
				String requestedPath = firstLineArgs[1];
				version = firstLineArgs[2];
				
				if(!method.equals("GET") || (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1"))) {
					sendError(ostream, 400, error400);
					return;
				}
				
				host = domainName;
				for(String line : lines) {
					if(line.substring(0, 6).toLowerCase().equals("host: ")) {
						String hostName = line.substring(6);
						if(hostName.contains(":")) {
							hostName = hostName.substring(0, hostName.indexOf(":"));
						}
						host = hostName;
						break;
					}
				}
				
				synchronized(this) {
					SID = checkSession(lines);
					permParams = sessions.get(SID).getMap();
					outputCookies.add(new RCCookie("sid", SID, null, host, "\\", true));
				}
				
				String path = "";
				String parameters = "";
				if(requestedPath.contains("?")) {
					path = requestedPath.substring(0, requestedPath.indexOf("?"));
					parameters = requestedPath.substring(requestedPath.indexOf("?")+1);
					String[] parameterValues = parameters.split("\\&");
					
					for(String s : parameterValues) {
						String[] paramVal = s.split("=");
						if(paramVal.length != 2) {
							sendError(ostream, 400, error400);
							return;
						}
						params.put(paramVal[0], paramVal[1]);
					}
				} else {
					path = requestedPath;
				}
				
				Path p = Paths.get(path.substring(1));
				internalDispatchRequest(p.toString(), true);
				
				istream.close();
				ostream.close();
				csocket.close();
			} catch(Exception e) {
				throw new IllegalStateException(e.getMessage());
			}
		}
		
		/**
		 * Checks if there is already a session for the sid found in the given lines of request
		 * 
		 * @return Either a sid of the existing session, or of a new one.
		 */
		private String checkSession(List<String> lines) {
			String sidCandidate = null;
			
			l: for(String line : lines) {
				if(line.startsWith("Cookie: ")) {
					line = line.substring(8);
					String[] cookies = line.split(";");
					for(String cookie : cookies) {
						if(cookie.trim().startsWith("sid")) {
							sidCandidate = cookie.trim().substring(5, cookie.trim().length()-1);
							break l;
						}
					}
				}
			}
		
			if(sidCandidate == null || sessions.get(sidCandidate) == null ||
					!sessions.get(sidCandidate).getHost().equals(host)) {
				return createNewSession();
			}
			
			if(sessions.get(sidCandidate).getValidUntil() < System.currentTimeMillis()/1000) {
				sessions.remove(sidCandidate);
				return createNewSession();
			}
			
			sessions.get(sidCandidate).setValidUntil(System.currentTimeMillis()/1000 + sessionTimeout);
			return sidCandidate;
		}
		
		/**
		 * Creates a new session.
		 * 
		 * @return New sid
		 */
		private String createNewSession() {
			String sidNew = "";
			for(int i = 0; i < sidLength; i++) {
				sidNew += sessionRandom.nextInt(10);
			}
			SessionMapEntry sme = new SessionMapEntry(sidNew, host,
					sessionTimeout + System.currentTimeMillis()/1000);
			sessions.put(sidNew, sme);
			return sidNew;
		}
		
		/**
		 * Reads all request header bytes from the input stream
		 *  
		 * @param is input stream
		 * @return Request header as bytes
		 * @throws IOException
		 */
		private byte[] readRequest(InputStream is) throws IOException {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int state = 0;
		l:		while(true) {
					int b = is.read();
					if(b==-1) return null;
					if(b!=13) {
						bos.write(b);
					}
					switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
					}
				}
				return bos.toByteArray();
		}
		
		/**
		 * Extracts lines from the given header
		 * 
		 * @param requestHeader Header of the request
		 * @return List of lines
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}
		
		/**
		 * Sends an error with the given code and code text to the client.
		 * 
		 * @param cos otuput stream
		 * @param statusCode status code
		 * @param statusText status code information
		 * @throws IOException
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {
				cos.write(
					("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
				cos.flush();
			}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Takes a path to the requested file. If directCall is set to true, private files cannot
		 * be accessed.
		 * 
		 * @param p Path to the requested file 
		 * @param directCall Is the call direct
		 * @throws Exception
		 */
		public void internalDispatchRequest(String p, boolean directCall) throws Exception {
			if(directCall) {
				if(p.startsWith(privateFolder)) {
					sendError(ostream, 404, error404);
					return;
				}
			}
			
			if(p.toString().startsWith(workerPrefix)) {
				String workersPackage = "hr.fer.zemris.java.webserver.workers.";
				String fqcn = workersPackage + p.substring(workerPrefix.length());
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				iww.processRequest(getRequestContext());
				return;
			}
			
			IWebWorker w = workersMap.get(p);
			if(w != null) {
				w.processRequest(getRequestContext());
				return;
			}
			
			Path resolvedPath = documentRoot.resolve(p);
			if(!resolvedPath.toAbsolutePath().startsWith(documentRoot.toAbsolutePath())) {
				sendError(ostream, 403, error403);
				return;
			}
			
			if(!resolvedPath.toFile().exists() || !resolvedPath.toFile().canRead()) {
				sendError(ostream, 404, error404);
				return;
			}
			
			String fileName = resolvedPath.getFileName().toString();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			if(extension.equals(scriptExtension)) {
				String script = readFromDisk(resolvedPath.toString());
				RequestContext rc = getRequestContext();
				DocumentNode node = new SmartScriptParser(script).getDocumentNode();
				SmartScriptEngine engine = new SmartScriptEngine(node, rc);
				engine.execute();
			} else {
				String mime = mimeTypes.get(extension);
				mime = mime == null ? defaultMime : mime;
				
				RequestContext rc = getRequestContext();
				rc.setMimeType(mime);
				rc.setStatusCode(200);
				
				InputStream iss = Files.newInputStream(resolvedPath);
				byte[] buffer = new byte[1024];
				while(true) {
					int r = iss.read(buffer);
					if(r < 1) break;
					
					if(r < 1024) {
						byte[] buff = new byte[r];
						for(int i = 0; i < r; i++) {
							buff[i] = buffer[i];
						}
						rc.write(buff);
					} else {
						rc.write(buffer);
					}
				}
				
				iss.close();
			}
		}
		
		/**
		 * Reads the whole file from the disk for the given path.
		 * 
		 * @param filepath Path to the file.
		 * @return File as string.
		 * @throws IOException
		 */
		private String readFromDisk(String filepath) throws IOException {
			String documentBody = "";
			
			InputStream inputStream = Files.newInputStream(Paths.get(filepath));
			byte[] buffer = new byte[1024];
			while(true) {
				int r = inputStream.read(buffer);
				if(r < 1) break;
				if(r < 1024) {
					byte[] buff = new byte[r];
					for(int i = 0; i < r; i++) {
						buff[i] = buffer[i];
					}
					documentBody += new String(buff);
				} else {
					documentBody += new String(buffer);
				}
			}
			
			return documentBody;
		}
	}
	
	/**
	 * Main method. Starts the server.
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("config/server.properties");
		server.start();
	}
}
