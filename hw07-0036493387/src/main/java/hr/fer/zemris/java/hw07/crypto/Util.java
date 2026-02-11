package hr.fer.zemris.java.hw07.crypto;

import java.util.Objects;

/**
 * This is an auxiliary class with some methods used by the program Crypto.
 * 
 * @author Mihael Stočko
 *
 */
public class Util {

	/**
	 * Converts a string to a byte array.
	 * 
	 * @param s String to be converted
	 * @return A byte array got from the string given.
	 * @throws IllegalArgumentException
	 */
	public static byte[] hextobyte(String s) {
		Objects.requireNonNull(s, "null cannot be converted to a byte array.");
		
		if(s.length() % 2 != 0) {
			throw new IllegalArgumentException("The string to be converted to byte array cannot have an odd"
					+ "number of characters.");
		}
		
		byte[] byteArray = new byte[s.length()/2];
		
		for(int i = 0, l = s.length()-1; i < l; i+=2) {
			if(Character.isDigit(s.charAt(i))) {
				byteArray[i/2] += Character.getNumericValue(s.charAt(i));
			} else {
				byteArray[i/2] += getNumber(s.charAt(i));
			}
			
			byteArray[i/2] = (byte)(byteArray[i/2] << 4);
			
			if(Character.isDigit(s.charAt(i+1))) {
				byteArray[i/2] += Character.getNumericValue(s.charAt(i+1));
			} else {
				byteArray[i/2] += getNumber(s.charAt(i+1));
			}
		}
		
		return byteArray;
	}
	
	/**
	 * Converts a hex character to a number.
	 * 
	 * @param c Hex character (a-e) to be converted.
	 * @return The resulting number
	 */
	private static byte getNumber(char c) {
		c = Character.toLowerCase(c);
		switch(c) {
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		default:
			throw new IllegalArgumentException(c + " is not a valid hex character.");
		}
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
}
