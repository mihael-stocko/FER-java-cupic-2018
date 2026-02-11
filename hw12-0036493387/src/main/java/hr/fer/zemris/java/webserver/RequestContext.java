package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class is used for writing a response to client requests.
 * Before writing anything, it creates a header that can be configured by using this
 * class' methods.
 * 
 * @author Mihael Stočko
 *
 */
public class RequestContext {

	/**
	 * This class respresents a cookie for storing session data.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	public static class RCCookie {
		/**
		 * Name of the cookie
		 */
		private String name;
		
		/**
		 * Value of the cookie
		 */
		private String value;
		
		/**
		 * Domain
		 */
		private String domain;
		
		/**
		 * Path
		 */
		private String path;
		
		/**
		 * Max-age
		 */
		private Integer maxAge;
		
		/**
		 * Is the cookie http only
		 */
		private boolean httpOnly;
		
		/**
		 * Constructor.
		 */
		public RCCookie(String name, String value, Integer maxAge, 
				String domain, String path, boolean httpOnly) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}
		
		/**
		 * Getter for name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Getter for value
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Getter for domain
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Getter for path
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Getter for max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}
	
	/**
	 * Output stream of the request
	 */
	private OutputStream outputStream;
	
	/**
	 * Encoding charset
	 */
	private Charset charset;
	
	/**
	 * Encoding
	 */
	public String encoding = "UTF-8";
	
	/**
	 * Status code
	 */
	public int statusCode = 200;
	
	/**
	 * Status code description
	 */
	public String statusText = "OK";
	
	/**
	 * Mime type
	 */
	public String mimeType = "text/html";
	
	/**
	 * Parameters
	 */
	private Map<String, String> parameters;
	
	/**
	 * Temporary parameters
	 */
	private Map<String, String> temporaryParameters = new HashMap<String, String>();
	
	/**
	 * Persistent parameters
	 */
	private Map<String, String> persistentParameters;
	
	/**
	 * List of output cookies
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Has the header been generated
	 */
	private boolean headerGenerated = false;
	
	/**
	 * A dispatcher for this request
	 */
	private IDispatcher dispatcher;
	
	/**
	 * Getter for dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Getter for output stream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	/**
	 * Setter for output stream
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * Getter for charset
	 */
	public Charset getCharset() {
		return charset;
	}
	
	/**
	 * Setter for charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * Setter for encoding
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) throw new IllegalStateException("Header has already been created.");
		this.encoding = encoding;
	}

	/**
	 * Setter for status code
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) throw new IllegalStateException("Header has already been created.");
		this.statusCode = statusCode;
	}

	/**
	 * Setter for status code description
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) throw new IllegalStateException("Header has already been created.");
		this.statusText = statusText;
	}

	/**
	 * Setter for mime type
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) throw new IllegalStateException("Header has already been created.");
		this.mimeType = mimeType;
	}

	/**
	 * Has the header been generated
	 */
	public boolean isHeaderGenerated() {
		return headerGenerated;
	}

	/**
	 * Setter for headerGenerated
	 */
	public void setHeaderGenerated(boolean headerGenerated) {
		this.headerGenerated = headerGenerated;
	}
	
	/**
	 * Constructor.
	 * 
	 * @throws NullPointerException
	 */
	public RequestContext(
			OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		Objects.requireNonNull(outputStream, "Output stream cannot be null.");
		if(parameters == null) {
			this.parameters = new HashMap<>();
		}
		if(persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		}
		if(outputCookies == null) {
			this.outputCookies = new LinkedList<>();
		}
		
		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
	}
	
	/**
	 * Constructor.
	 *
	 * @throws NullPointerException
	 */
	public RequestContext(
			OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters,
			IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		
		Objects.requireNonNull(dispatcher, "Dispatcher cannot be null.");
		this.dispatcher = dispatcher;
		
		if(temporaryParameters == null) {
			this.temporaryParameters = new HashMap<>();
		}
		this.temporaryParameters = temporaryParameters;
	}
	
	/**
	 * Gets a parameter from the map for the given name.
	 * 
	 * @param name Key of the parameter
	 * @return Parameter from the parameters map
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Returns a set of all parameter names
	 */
	public Set<String> getParameterNames() {
		Set<String> content = new HashSet<>();
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			content.add(entry.getKey());
		}
		
		return Collections.unmodifiableSet(content);
	}
	
	/**
	 * Gets a persistent parameter from the map for the given name.
	 * 
	 * @param name Key of the parameter
	 * @return Parameter from the persistent parameters map
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Returns a set of all persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		Set<String> content = new HashSet<>();
		for(Map.Entry<String, String> entry : persistentParameters.entrySet()) {
			content.add(entry.getKey());
		}
		
		return Collections.unmodifiableSet(content);
	}
	
	/**
	 * Sets the persistent parameter mapped to the key name to the given value.
	 *
	 * @throws NullPointerException
	 */
	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name, "Name cannot be null.");
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes the parameter mapped to the given name from the persistent parameters map.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Gets a persistent parameter from the map for the given name.
	 * 
	 * @param name Key of the parameter
	 * @return Parameter from the temporary parameters map
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Returns a set of all temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		Set<String> content = new HashSet<>();
		for(Map.Entry<String, String> entry : temporaryParameters.entrySet()) {
			content.add(entry.getKey());
		}
		
		return Collections.unmodifiableSet(content);
	}
	
	/**
	 * Sets the temporary parameter mapped to the key name to the given value.
	 *
	 * @throws NullPointerException
	 */
	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name, "Name cannot be null.");
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes the parameter mapped to the given name from the temporary parameters map.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Adds an {@link RCCookie} to the list of cookies.
	 * 
	 * @throws NullPointerException
	 */
	public void addRCCookie(RCCookie cookie) {
		if(headerGenerated) throw new IllegalStateException("Header has already been created.");
		Objects.requireNonNull(cookie, "Cookie cannot be null");
		outputCookies.add(cookie);
	}
	
	/**
	 * Writes the given byte array to the output stream. First time it is called it outputs
	 * the generated header.
	 * 
	 * @param data Data to be written.
	 * @return This context
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			createHeader();
		}
		
		outputStream.write(data);
		
		return this;
	}
	
	/**
	 * Writes the given String to the output stream. First time it is called it outputs
	 * the generated header.
	 * 
	 * @param data String to be written.
	 * @return This context
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			charset = Charset.forName(encoding);
		}
		
		return write(text.getBytes(charset));
	}
	
	/**
	 * Creates a header using all of the parameters it was configured with.
	 * 
	 * @throws IOException
	 */
	private void createHeader() throws IOException {
		headerGenerated = true;
		charset = Charset.forName(encoding);
		
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 ");
		sb.append(statusCode);
		sb.append(' ');
		sb.append(statusText);
		sb.append("\r\n");
		
		sb.append("Content-Type: ");
		sb.append(mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset=");
			sb.append(encoding);
		}
		sb.append("\r\n");
		
		for(RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: ");
			
			if(cookie.name != null) {
				sb.append(cookie.name);
				sb.append("=\"");
				sb.append(cookie.value);
				sb.append("\"");
				sb.append("; ");
			}
			
			if(cookie.domain != null) {
				sb.append("Domain=");
				sb.append(cookie.domain);
				sb.append("; ");
			}
			
			if(cookie.path != null) {
				sb.append("Path=");
				sb.append(cookie.path);	
				sb.append("; ");
			}
			
			if(cookie.maxAge != null) {
				sb.append("Max-Age=");
				sb.append(cookie.maxAge);	
				sb.append("; ");
			}
			
			if(cookie.httpOnly) {
				sb.append("HttpOnly  ");
			}
			
			sb.setLength(sb.length() - 2);
			
			sb.append("\r\n");
		}
		
		sb.append("\r\n");
		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
	}
}
