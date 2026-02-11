package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program od korisnika uzima dva argumenta koji predstavljaju širinu i visinu pravokutnika
 * te izračunava njegov opseg i površinu.
 * 
 * @author Mihael Stočko
 * @version 1.0
 */
public class Rectangle {
	
	/**
	 * main metoda koja se počinje izvršavati prilikom pokretanja programa
	 * Argumente je moguće predati preko naredbenog retka ili interaktivno.
	 * U slučaju da se preko naredbenog retka ne preda niti jedan argument,
	 * program će interaktivno tražiti podatke. Pritom argumenti moraju biti
	 * pozitivni realni brojevi.
	 * 
	 * @param args Prvi argument predstavlja širinu, a drugi visinu
	 */
	public static void main(String[] args) {
		if(args.length == 2) {
			try {
				double sirina = Double.parseDouble(args[0]);
				double visina = Double.parseDouble(args[1]);
				ispis(sirina, visina);
			} catch(Exception ex) {
				System.out.println("Pogrešan format parametara.");
			}
		} else if(args.length == 0) {
			double sirina, visina;
			Scanner sc = new Scanner(System.in);
			sirina = unos('s', sc);
			visina = unos('v', sc);
			sc.close();
			ispis(sirina, visina);
		} else {
			System.out.println("Broj argumetana je neispravan.");
		}
	}
	
	/**
	 * Metoda se koristi za interaktivno uzimanje argumenata od korisnika.
	 * 
	 * @param c Ako je <code>'s'</code> unosi se širina, inače visina.
	 * @param sc Objekt <code>Scanner</code> koji mora biti stvoren izvan metode i koji se koristi za učitavanje podataka
	 * @return Učitana vrijednost
	 */
	public static double unos(char c, Scanner sc) {
		double vrijednost;
		while(true) {
			if(c == 's') {
				System.out.print("Unesite širinu > ");
			} else {
				System.out.print("Unesite visinu > ");
			}
			if(sc.hasNextDouble()) {
				vrijednost = sc.nextDouble();
				if(vrijednost < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else if (vrijednost < 1E-6) {
					System.out.println("Nula nije valjan argument.");
				} else {
					break;
				}
			} else {
				String unos = sc.next();
				System.out.println("'" + unos + "' se ne može protumačiti kao broj.");
			}
		}
		return vrijednost;
	}
	
	/**
	 * Metoda ispisuje širinu i visinu pravokutnika na standardni izlaz.
	 * 
	 * @param sirina Širina pravokutnika
	 * @param visina Visina pravokutnika
	 */
	public static void ispis(double sirina, double visina) {
		double opseg = opseg(sirina, visina);
		double povrsina = povrsina(sirina, visina);
		System.out.println("Pravokutnik širine " + sirina + " i visine " + visina + 
				" ima površinu " + povrsina + " te opseg " + opseg + ".");
	}
	
	/**
	 * Izračunava opseg pravokutnika za predanu širinu i visinu.
	 * 
	 * @param sirina Širina pravokutnika
	 * @param visina Visina pravokutnika
	 * @return Opseg pravokutnika
	 */
	public static double opseg(double sirina, double visina) {
		if(sirina <= 0 || visina <= 0) {
			throw new IllegalArgumentException();
		}
		return 2*sirina + 2*visina;
	}
	
	/**
	 * Izračunava površinu pravokutnika za predanu širinu i visinu.
	 * 
	 * @param sirina Širina pravokutnika
	 * @param visina Visina pravokutnika
	 * @return Površina pravokutnika
	 */
	public static double povrsina(double sirina, double visina) {
		if(sirina <= 0 || visina <= 0) {
			throw new IllegalArgumentException();
		}
		return sirina*visina;
	}
}
