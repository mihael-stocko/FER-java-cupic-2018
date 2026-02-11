package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.security.MessageDigest;
import java.util.Objects;

/**
 * This class contains some auxiliary methods used by the blog.
 * 
 * @author Mihael Stočko
 *
 */
public class Util {

	/**
	 * Retrns an empty string if the provided string is null.
	 * Otherwise trims it.
	 * 
	 * @return prepared string
	 */
	public static String pripremi(String s) {
		if(s==null) return "";
		return s.trim();
	}
	
	/**
	 * Converts a byte array to a string.
	 * 
	 * @param bytes Byte array to be converted
	 * @return A string got from the given byte array.
	 * @throws NullPointerException
	 */
	public static String bytetohex(byte[] bytes) {
		Objects.requireNonNull(bytes, "null cannot be converted to a String.");
		
		StringBuilder sb = new StringBuilder();
		for(byte b : bytes) {
			sb.append(getCharacter((byte)((b >> 4) & 0b00001111)));
			sb.append(getCharacter((byte)(b & 0b00001111)));
		}
		
		return sb.toString();
	}
	
	/**
	 * Converts a number to a hex character.
	 * 
	 * @param b Number to be converted.
	 * @return The resulting hex character (a-e)
	 */
	private static char getCharacter(byte b) {
		if(b < 10) {
			return (char)(b + '0');
		}
		
		switch(b) {
		case 10:
			return 'a';
		case 11:
			return 'b';
		case 12:
			return 'c';
		case 13:
			return 'd';
		case 14:
			return 'e';
		case 15:
			return 'f';
		default:
			throw new IllegalArgumentException(b + " cannot be converted to a hex character.");
		}
	}
	
	/**
	 * Returns the SHA-1 digest for the given string.
	 * 
	 * @param message Message to be digested
	 * @return digest
	 */
	public static String getDigest(String message) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		sha.update(message.getBytes());
		
		return Util.bytetohex(sha.digest());
	}
}
