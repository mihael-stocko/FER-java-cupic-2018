package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * This program takes one argument - the path to a folder containing text documents.
 * It analyzes the documents and builds a vocabulary. The command prompt is then started
 * and the user can search for the text documents by giving a query of keywords.
 * 
 * @author Mihael Stočko
 *
 */
public class Konzola {
	
	/**
	 * This is an auxiliary class that represents a single document.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	private static class Document {
		
		/**
		 * Path to the document
		 */
		private Path path;
		
		/**
		 * Vector of words
		 */
		private double[] vector;
		
		/**
		 * Similarity to the query
		 */
		private Double similarity;
		
		/**
		 * Constructor.
		 */
		public Document() {
			super();
		}

		/**
		 * Getter for path
		 */
		public Path getPath() {
			return path;
		}

		/**
		 * Setter for path
		 */
		public void setPath(Path path) {
			this.path = path;
		}

		/**
		 * Getter for vector
		 */
		public double[] getVector() {
			return vector;
		}

		/**
		 * Setter for vector
		 */
		public void setVector(double[] vector) {
			this.vector = vector;
		}

		/**
		 * Getter for similarity
		 */
		public Double getSimilarity() {
			return similarity;
		}

		/**
		 * Setter for similarity
		 */
		public void setSimilarity(double similarity) {
			this.similarity = similarity;
		}
	}
	
	/**
	 * Main method
	 * 
	 * @param args The path to a folder with documents.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.out.println("1 argument expected. Terminating.");
			return;
		}
		
		Path path = Paths.get(args[0]);
		if(!path.toFile().isDirectory()) {
			System.out.println("A path to a directory expected. Terminating.");
			return;
		}
		
		Set<String> vocab = new HashSet<>();
		Files.walkFileTree(Paths.get(path.toUri()), new FileVisitor<Path>() {
			@Override
			public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
				List<String> words = readWords(arg0);
				vocab.addAll(words);
				
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});
		
		Set<String> stopWords = new HashSet<>();
		stopWords.addAll(readWords(Paths.get("src/main/resources/stopWords.txt")));
		
		vocab.removeAll(stopWords);
		List<String> vocabulary = new LinkedList<>(vocab);
		
		System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.");
		
		List<Document> documents = new LinkedList<>();
		List<double[]> vectors = new LinkedList<>();
		Files.walkFileTree(Paths.get(path.toUri()), new FileVisitor<Path>() {
			@Override
			public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
				double[] vector = new double[vocabulary.size()];
				
				List<String> words = readWords(arg0);
				for(String s : words) {
					if(vocabulary.contains(s)) {
						vector[vocabulary.indexOf(s)]++;
					}
				}
				
				vectors.add(vector);
				
				Document doc = new Document();
				doc.setPath(arg0);
				doc.setVector(vector);
				documents.add(doc);
				
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});
		
		long numOfDocuments = documents.size();
		
		double[] idf = new double[vocabulary.size()];
		for(int i = 0; i < idf.length; i++) {
			int wordFrequency = 0;
			for(double[] vector : vectors) {
				if(vector[i] > 0) {
					wordFrequency++;
				}
			}
			idf[i] = Math.log10((double)numOfDocuments/wordFrequency);
		}
		
		for(double[] vector : vectors) {
			for(int i = 0; i < vector.length; i++) {
				vector[i] *= idf[i];
			}
		}
		
		List<Document> bestDocs = new LinkedList<>();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("> ");
		while(true) {
			String l = sc.nextLine();
			
			String[] line = l.split(" ", 2);
			
			String commandName = line[0];
			if(commandName.equals("exit")) {
				break;
			} else if(commandName.equals("results")) {
				if(bestDocs.size() == 0) {
					System.out.println("This command can be called only after at "
							+ "least one query has been executed.");
					System.out.println("> ");
				} else {
					for(int i = 0; i < bestDocs.size(); i++) {
						System.out.print("[" + i + "] (" + 
					new DecimalFormat("0.0000").format(bestDocs.get(i).getSimilarity()) + ") ");
						System.out.println(bestDocs.get(i).getPath());
					}
					System.out.print("> ");
				}
				continue;
			}
			
			if(line.length < 2) {
				System.out.println("Invalid command.");
				System.out.print("> ");
				continue;
			}
			
			if(commandName.equals("query")) {
				List<String> words = readWords(line[1]);
				System.out.print("Query is: [");
				for(int i = 0; i < words.size()-1; i++) {
					System.out.print(words.get(i) + ", ");
				}
				System.out.println(words.get(words.size()-1) + "]");
				
				double[] tfidf = new double[vocabulary.size()];
				
				for(String word : words) {
					if(vocabulary.contains(word)) {
						tfidf[vocabulary.indexOf(word)] += 1;
					}
				}
				
				for(int i = 0; i < tfidf.length; i++) {
					tfidf[i] *= idf[i];
				}
				
				for(Document doc : documents) {
					doc.setSimilarity(scalarProduct(doc.getVector(), tfidf)/norm(doc.getVector())/norm(tfidf));
				}
				
				System.out.println("Najboljih 10 rezultata:");
				Collections.sort(documents, new Comparator<Document>() {
					@Override
					public int compare(Document arg0, Document arg1) {
						return arg1.getSimilarity().compareTo(arg0.getSimilarity());
					}
				});
				
				bestDocs.clear();
				for(int i = 0; i < 10; i++) {
					if(documents.get(i).getSimilarity() > 0) {
						bestDocs.add(documents.get(i));
					} else {
						break;
					}
				}
				
				for(int i = 0; i < bestDocs.size(); i++) {
					System.out.print("[" + i + "] (" + 
						new DecimalFormat("0.0000").format(bestDocs.get(i).getSimilarity()) + ") ");
					System.out.println(bestDocs.get(i).getPath());
				}
				
			} else if(commandName.equals("type")) {
				if(bestDocs.size() == 0) {
					System.out.println("This command can be called only after at "
							+ "least one query has been executed.");
				} else {
					if(line[1].length() != 1 || !Character.isDigit(line[1].charAt(0))) {
						System.out.println("Argument for this command must be a single digit.");
					} else {
						int id = Integer.parseInt(line[1]);
						if(id >= bestDocs.size()) {
							System.out.println("Argument for this command must be smaller than the number"
									+ " of results.");
							System.out.print("> ");
							continue;
						}
						Path p = bestDocs.get(id).getPath();
						StringBuilder sb = new StringBuilder();
						for(int i = 0; i < p.toString().length(); i++) {
							sb.append('-');
						}
						System.out.println(sb.toString());
						System.out.println(p.toString());
						System.out.println(sb.toString());
						System.out.println();
						List<String> lines = Files.readAllLines(p);
						for(String s : lines) {
							System.out.println(s);
						}
					}
				}
			} else {
				System.out.println("Unsupported command.");
			}
			
			System.out.print("> ");
		}
		sc.close();
	}
	
	/**
	 * Calculates the scalar product of the given vectors.
	 * 
	 * @param vector1 First vector
	 * @param vector2 First vector
	 * @return Scalar product
	 */
	private static double scalarProduct(double[] vector1, double[] vector2) {
		if(vector1.length != vector2.length) {
			throw new IllegalArgumentException("The two provided vectors must be of same length.");
		}
		
		double result = 0;
		for(int i = 0; i < vector1.length; i++) {
			result += vector1[i]*vector2[i];
		}
		
		return result;
	}
	
	/**
	 * Calculates the norm of the given vector.
	 * 
	 * @return Norm
	 */
	private static double norm(double[] vector) {
		double result = 0;
		
		for(double d : vector) {
			result += Math.pow(d, 2);
		}
		
		return Math.sqrt(result);
	}
	
	/**
	 * Reads all words from the document given by path.
	 * 
	 * @param path Path to the document
	 * @return List of words
	 * @throws IOException
	 */
	public static List<String> readWords(Path path) throws IOException {
		return readWords(Files.readAllLines(Paths.get(path.toUri())));
	}
	
	/**
	 * Reads all words from the given string.
	 * 
	 * @param string String that can contain multiple words
	 * @return List of words
	 * @throws IOException
	 */
	public static List<String> readWords(String string) throws IOException {
		List<String> list = new LinkedList<String>();
		list.add(string);
		return readWords(list);
	}
	
	/**
	 * Reads all words from the given list of strings.
	 * 
	 * @param content List of strings of which every one can contain multiple words.
	 * @return List of words
	 * @throws IOException
	 */
	public static List<String> readWords(List<String> content) throws IOException {
		List<String> words = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		for(String s : content) {
			for(int i = 0; i < s.length(); i++) {
				if(Character.isAlphabetic(s.charAt(i))) {
					sb.append(s.toLowerCase().charAt(i));
				} else {
					if(sb.length() > 0) {
						words.add(sb.toString());
						sb.setLength(0);
					}
				}
			}
			if(sb.length() > 0) {
				words.add(sb.toString());
				sb.setLength(0);
			}
		}
		
		if(sb.length() > 0) {
			words.add(sb.toString());
			sb.setLength(0);
		}
		
		return words;
	}
}
