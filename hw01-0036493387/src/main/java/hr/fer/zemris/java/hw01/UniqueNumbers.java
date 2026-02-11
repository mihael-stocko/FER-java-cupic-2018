package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program omogućava korisniku unos brojeva koje potom pohranjuje u uređeno binarno stablo.
 * Nakon unosa cijelo se stablo ispisuje.
 * 
 * @author Mihael Stočko
 * @version 1.0
 */
public class UniqueNumbers {
	
	/**
	 * Pomoćna struktura koja se koristi za pohranu brojeva u stablo
	 * 
	 * @author Mihael Stočko
	 * @version 1.0
	 */
	public static class TreeNode {
		public TreeNode left, right;
		public int value;
	}
	
	/**
	 * main metoda koja se počinje izvršavati prilikom pokretanja programa
	 * 
	 * @param args Ulazni argumenti se ne koriste.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;
		while(true) {
			System.out.print("Unesite broj > ");
			if(sc.hasNextInt()) {
				int broj = sc.nextInt();
				if(containsValue(glava, broj)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					glava = addNode(glava, broj);
					System.out.println("Dodano.");
				}
			} else {
				String unos = sc.next();
				if(unos.equals("kraj")) {
					break;
				}
				System.out.println("'" + unos + "' nije cijeli broj.");
			}
		}
		sc.close();
		System.out.print("Ispis od najmanjeg: ");
		ispisOdManjeg(glava);
		System.out.println();
		System.out.print("Ispis od najvećeg: ");
		ispisOdVeceg(glava);
	}
	
	/**
	 * Umeće predani broj u stablo na koje pokazuje <code>glava</code> samo ako se taj
	 * broj već ne nalazi u stablu.
	 * 
	 * @param glava Glava binarnog stabla
	 * @param vrijednost Broj koji se ubacuje u stablo
	 * @return Glava novonastalog stabla
	 */
	public static TreeNode addNode(TreeNode glava, int vrijednost) {
		if(glava == null) {
			TreeNode cvor = new TreeNode();
			cvor.left = null;
			cvor.right = null;
			cvor.value = vrijednost;
			return cvor;
		} else if(glava.value == vrijednost) {
			return glava;
		} else if(glava.value < vrijednost) {
			glava.right = addNode(glava.right, vrijednost);
			return glava;
		} else {
			glava.left = addNode(glava.left, vrijednost);
			return glava;
		}
	}
		
	/**
	 * Vraća broj čvorova u binarnom stablu
	 * 
	 * @param glava Glava binarnog stabla 
	 * @return Broj čvorova u stablu
	 */
	public static int treeSize(TreeNode glava) {
		if(glava == null) {
			return 0;
		} else {
			return 1 + treeSize(glava.left) + treeSize(glava.right);
		}
	}

	/**
	 * Provjerava sadrži li binarno stablo zadani broj
	 * 
	 * @param glava Glava binarnog stabla
	 * @param vrijednost Broj za koji se provjerava sadrži li ga stablo
	 * @return <code>true</code> ako se broj nalazi u stablu, inače <code>false</code>
	 */
	public static boolean containsValue(TreeNode glava, int vrijednost) {
		if(glava == null) {
			return false;
		} else if(glava.value == vrijednost){
			return true;
		} else if(glava.value < vrijednost) {
			return containsValue(glava.right, vrijednost);
		} else {
			return containsValue(glava.left, vrijednost);
		}
	}
	
	/**
	 * Metoda ispisuje cijelo stablo od najmanjeg prema najvećem elementu
	 * 
	 * @param glava Glava binarnog stabla
	 */
	public static void ispisOdManjeg(TreeNode glava) {
		if(glava == null) {
			return;
		}
		ispisOdManjeg(glava.left);
		System.out.print(glava.value + " ");
		ispisOdManjeg(glava.right);
	}
	
	/**
	 * Metoda ispisuje cijelo stablo od najvećeg prema najmanjem elementu
	 * 
	 * @param glava Glava binarnog stabla
	 */
	public static void ispisOdVeceg(TreeNode glava) {
		if(glava == null) {
			return;
		}
		ispisOdVeceg(glava.right);
		System.out.print(glava.value + " ");
		ispisOdVeceg(glava.left);
	}
}
