package hr.fer.zemris.java.hw07.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This program offers an option to calculate the message digest from a file
 * and to encrypt/decrypt a given file. The program takes 2 or 3 arguments, depending
 * on the first one. If the first argument is "checksha", the second one should be the
 * expected digest. If the first argument is "encrypt" or "decrypt", the second one should
 * be the source file and the third one should be the destination.
 * 
 * @author Mihael Stočko
 *
 */
public class Crypto {
	/**
	 * Length of the message digest
	 */
	private static int digestLength = 64;
	
	/**
	 * Length of the password used for encryption and decryption
	 */
	private static int passwordLength = 32;
	
	/**
	 * Length of the vector used for encryption and decryption
	 */
	private static int vectorLength = 32;

	/**
	 * Size of the buffer used for reading and writing to and from files.
	 */
	private static int bufferSize = 1024;
	
	/**
	 * Checksha command
	 */
	private static String checksha = "checksha";
	
	/**
	 * Encrypt command
	 */
	private static String encrypt = "encrypt";
	
	/**
	 * Decrypt command
	 */
	private static String decrypt = "decrypt";
	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("You must provide program arguments.");
			return;
		}
		
		if(args[0].equals(checksha)) {
			messageDigest(args);
		} else if(args[0].equals(encrypt)) {
			crypt(args, true);
		} else if(args[0].equals(decrypt)) {
			crypt(args, false);
		} else {
			System.out.println("Unsupported command.");
		}
	}
	
	/**
	 * Checks if the digest of the given file equals the expected digest.
	 * Prints the conclusion on the console.
	 * 
	 * @param args
	 */
	private static void messageDigest(String[] args) {
		if(args.length != 2) {
			System.out.println("checksha expects one argument.");
			return;
		}
		Path p = Paths.get(args[1]);
		if(!Files.exists(p)) {
			System.out.println("The given file does not exist.");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Please provide expected sha-256 digest for " + args[1] + ":\n> ");
		String expectedDigest = Crypto.getHexString(digestLength, sc);
		sc.close();
		String digest = Crypto.getDigest(p);
		if(digest == null) {
			return;
		}
		
		System.out.print("Digesting completed. ");
		if(expectedDigest.equals(digest)) {
			System.out.println("Digest of " + p.getFileName() + " matches the expected digest.");
		} else {
			System.out.println("Digest of " + p.getFileName() + " does not match the expected digest.");
			System.out.println("Digest was: " + digest);
		}
	}
	
	/**
	 * Calculates the digest of the given file.
	 * 
	 * @param p Path to the file of which the digest should be calculated.
	 * @return The calculated digest
	 */
	private static String getDigest(Path p) {
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch(Exception e) {
			System.out.println("Cannot create a MessageDigest object. Terminating.");
			return null;
		}
		
		try(InputStream is = new FileInputStream(p.toFile())) {
			byte[] buffer = new byte[bufferSize];
			while(true) {
				int r = is.read(buffer);
				if(r < 1) {
					break;
				}
				sha.update(Arrays.copyOf(buffer, r));
			}
		} catch(Exception e) {
			System.out.println("Cannot opet the file. Terminating.");
			return null;
		}
		return Util.bytetohex(sha.digest());
	}
	
	/**
	 * If the encrypt flag is set to true, it does the encryption.
	 * Otherwise, does the decryption.
	 * 
	 * @param args Arguments from the main method.
	 * @param encrypt flag
	 */
	private static void crypt(String[] args, boolean encrypt) {
		if(args.length != 3) {
			System.out.println("Two arguments expected.");
			return;
		}
		Path p1 = Paths.get(args[1]);
		Path p2 = Paths.get(args[2]);
		if(!Files.exists(p1)) {
			System.out.println("The given file does not exist.");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Please provide password as hex-encoded text (32 hex-digits):\n> ");
		String password = getHexString(passwordLength, sc);
		System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
		String vector = getHexString(vectorLength, sc);
		sc.close();
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(password), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(vector));
		
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch(Exception e) {
			System.out.println("Cannot create a Cipher object. Terminating.");
			return;
		}
	
		try (InputStream is = new FileInputStream(p1.toFile());
			OutputStream os = new FileOutputStream(p2.toFile())) {
			byte[] buffer = new byte[bufferSize];
			while(true) {
				int r = is.read(buffer);
				if(r < 1) {
					break;
				}
				byte[] bytes;
				if(r == bufferSize) {
					bytes = cipher.update(Arrays.copyOf(buffer, r));
				} else {
					bytes = cipher.doFinal(Arrays.copyOf(buffer, r));
				}
				os.write(bytes);
			}
		} catch(Exception e) {
			System.out.println("Cannot open a stream. Terminating.");
			return;
		}
		
		if(encrypt) {
			System.out.print("Encryption");
		} else {
			System.out.print("Decryption");
		}
		System.out.println(" completed. Generated file "
				+ p2.getFileName() + " based on " + p1.getFileName() + ".");
	}
	
	/**
	 * Reads a hex string of the specified length from the standard input.
	 * 
	 * @param length Expected string length
	 * @param sc Scanner object
	 * @return String that has been read
	 */
	private static String getHexString(int length, Scanner sc) {
		String s = null;
		
		while(sc.hasNext()) {
			s = sc.next();
			if(s.length() == length) {
				break;
			}
			System.out.println("Length of the expected string must be " + length + ".");
			System.out.print("> ");
		}
		
		return s;
	}
}
