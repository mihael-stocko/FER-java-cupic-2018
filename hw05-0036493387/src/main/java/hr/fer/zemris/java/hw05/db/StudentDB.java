package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The main program.
 * 
 * @author Mihael Stočko
 *
 */
public class StudentDB {
	
	/**
	 * Main method. Arguments are not used.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase studentDB = new StudentDatabase(lines);
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("> ");
		
		while(sc.hasNext()) {
			String line = sc.nextLine();
			
			if(line.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			if(!line.startsWith("query ")) {
				System.out.println("Invalid command.");
				System.out.println();
				System.out.print("> ");
				continue;
			}
			
			line = line.substring(6);
			QueryParser parser = null;
			try {
				parser = new QueryParser(line);
			} catch(Exception e) {
				System.out.println("Wrong input format: " + e.getMessage());
				System.out.println();
				System.out.print("> ");
				continue;
			}
			
			if(parser.isDirectQuery()) {
				System.out.println("Using index for record retrieval.");
				StudentRecord rec = studentDB.forJMBAG(parser.getQueriedJMBAG());
				printTable(rec.getJmbag().length(), rec.getSurname().length(), 
						rec.getName().length());
				System.out.println("| " + rec.getJmbag() + " | " + rec.getSurname() +
						" | " + rec.getName() + " | " + rec.getFinalGrade() + " |");
				printTable(rec.getJmbag().length(), rec.getSurname().length(), 
						rec.getName().length());
				System.out.println("Records selected: 1");
				System.out.println();
			} else {
				List<ConditionalExpression> list = parser.getQuery();
				List<StudentRecord> filtered = studentDB.filter(new QueryFilter(list));
				
				int a = 0;
				int b = 0;
				int c = 0;
				for(StudentRecord sr : filtered) {
					if(sr.getJmbag().length() > a) {
						a = sr.getJmbag().length();
					}
					if(sr.getSurname().length() > b) {
						b = sr.getSurname().length();
					}
					if(sr.getName().length() > c) {
						c = sr.getName().length();
					}
				}
				
				if(filtered.size() > 0) {
					printTable(a, b, c);
				}
				for(StudentRecord rec : filtered) {
					System.out.print("| " + rec.getJmbag() + " | " + rec.getSurname());
					for(int i = 0; i < b-rec.getSurname().length(); i++) {
						System.out.print(" ");
					}
					System.out.print(" | " + rec.getName());
					for(int i = 0; i < c-rec.getName().length(); i++) {
						System.out.print(" ");
					}
					System.out.println(" | " + rec.getFinalGrade() + " |");
				}
				if(filtered.size() > 0) {
					printTable(a, b, c);
				}
				System.out.println("Records selected: " + filtered.size());
				System.out.println();
			}
			
			System.out.print("> ");
		}
		
		sc.close();
	}
	
	/**
	 * Used to draw the table
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	private static void printTable(int a, int b, int c) {
		System.out.print("+=");
		for(int i = 0; i < a; i++) {
			System.out.print("=");
		}
		System.out.print("=+=");
		for(int i = 0; i < b; i++) {
			System.out.print("=");
		}
		System.out.print("=+=");
		for(int i = 0; i < c; i++) {
			System.out.print("=");
		}
		System.out.print("=+===+");
		System.out.println();
	}
}
