package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program od korisnika prima argumente funkcije faktorijela i ispisuje rezultate
 * sve dok korisnik ne upiše "kraj".
 * 
 * @author Mihael Stočko
 * @version 1.0
 */
public class Factorial {
	
	/**
	 * main metoda koja se počinje izvršavati prilikom pokretanja programa
	 * 
	 * @param args Ulazni argumenti se ne koriste.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print("Unesite broj > ");
			if(sc.hasNextInt()) {
				int broj = sc.nextInt();
				if(broj < 1 || broj > 20) {
					System.out.println("'" + broj + "' nije broj u dozvoljenom rasponu.");
				} else {
					long rezultat = faktorijela(broj);
					System.out.println(broj + "! = " + rezultat);
				}
			} else {
				String redak = sc.next();
				if(redak.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				} else {
					System.out.println("'" + redak + "' nije cijeli broj.");
				}
			}
		}
		sc.close();
	}
	
	/**
	 * Funkcija za ulazni argument računa funkciju faktorijela
	 * 
	 * @param broj Argument funkcije
	 * @return Vrijednost funkcije broj!
	 */
	public static long faktorijela(int broj) {
		if(broj < 0) {
			throw new IllegalArgumentException();
		}
		long rezultat = 1;
		for(int i = 1; i <= broj; i++) {
			rezultat *= i;
		}
		return rezultat;
	}
}
