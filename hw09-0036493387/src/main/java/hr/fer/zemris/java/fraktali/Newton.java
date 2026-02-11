package hr.fer.zemris.java.fraktali;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This program draws a Newton-Raphson fractal. It takes an arbitrary amount of complex numbers
 * and uses them for generating the fractal. It also uses parallelization to speed up the process.
 * 
 * @author Mihael Stočko
 *
 */
public class Newton {

	/**
	 * Main method.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n" + 
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> roots = readData();
		for(Complex c : roots) {
			System.out.println(c);
		}
		
		System.out.println("Image of the fractal will appear shortly. Thank you.");
		System.out.println();
		
		FractalViewer.show(new Producer(roots));
	}
	
	/**
	 * A model of the job for a single thread. It draws the fractal on the area given by
	 * the starting and the ending y coordinate.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	public static class Job implements Callable<Void> {
		/**
		 * Maximum number of iterations for a single pixel.
		 */
		static int maxIter = 16*16*16;
		
		/**
		 * Convergence threshold
		 */
		static double convergenceThreshold = 1e-3;
		
		/**
		 * Root closeness limit
		 */
		static double rootClosenessLimit = 1e-3;
		
		/**
		 * The polynomial used for generating the fractal
		 */
		private ComplexRootedPolynomial polynomial;
		
		/**
		 * The first derivative of the polynomial used for generating the fractal
		 */
		private ComplexPolynomial derived;
		
		/**
		 * Smallest real coordinate of the complex plain
		 */
		double reMin;
		
		/**
		 * Greatest real coordinate of the complex plain
		 */
		double reMax;
		
		/**
		 * Smallest imaginary coordinate of the complex plain
		 */
		double imMin;
		
		/**
		 * Greatest imaginary coordinate of the complex plain
		 */
		double imMax;
		
		/**
		 * Width of the screen
		 */
		int width;
		
		/**
		 * Height of the screen
		 */
		int height;
		
		/**
		 * Starting y for this thread
		 */
		int yMin;
		
		/**
		 * Ending y for this thread
		 */
		int yMax;
		
		/**
		 * Array that is to be filled with colors.
		 */
		short[] data;
		
		/**
		 * Constructor.
		 */
		public Job(double reMin, double reMax, double imMin, double imMax,
				int width, int height, int yMin, int yMax, ComplexRootedPolynomial polynomial, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.polynomial = polynomial;
			derived = polynomial.toComplexPolynom().derive();
			this.data = data;
		}
		
		/**
		 * This method is called by a thread. It calculates the color for each pixel and
		 * stores it into the array data.
		 */
		@Override
		public Void call() throws Exception {
			int offset = (yMin)*(width);
			
			for(int y = yMin; y <= yMax; y++) {
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					
					Complex zn = new Complex(cre, cim);
					Complex zn1;
					int iter = 0;
					
					double module = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						iter++;
						zn = zn1;
					} while(module > convergenceThreshold && iter < maxIter);
					
					int index = polynomial.indexOfClosestRootFor(zn1, rootClosenessLimit);
					if(index == -1) { 
						data[offset++] = 0;
					} else { 
						data[offset++] = (short)index;
					}
				}
			}
			
			return null;
		}
	}
	
	/**
	 * This class creates threads and divides the work between them.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	public static class Producer implements IFractalProducer {
		/**
		 * The polynomial used for generating the fractal
		 */
		private ComplexRootedPolynomial polynomial;
		
		/**
		 * Thread pool
		 */
		ExecutorService pool;
		
		/**
		 * Roots of the polynomial
		 */
		Complex[] roots;
		
		/**
		 * Constructor.
		 * @param roots Roots of the polynomial
		 */
		public Producer(List<Complex> roots) {
			this.roots = new Complex[roots.size()];
			int index = 0;
			for(Complex c : roots) {
				this.roots[index] = c;
				index++;
			}
			
			polynomial = new ComplexRootedPolynomial(this.roots);
			
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), 
					new DaemonicThreadFactory());
		}
		
		/**
		 * This method divides work between threads. It then waits for all the thread to finish
		 * and calls the method for drawing the fractal.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer) {
			
			short[] data = new short[width * height];
			
			final int numOfStripes = Runtime.getRuntime().availableProcessors()*8;
			int stripeHeight = height / numOfStripes;
			List<Future<Void>> results = new ArrayList<>();
			for(int i = 0; i < numOfStripes; i++) {
				int yMin = i*stripeHeight;
				int yMax = (i+1)*stripeHeight-1;
				if(i==numOfStripes-1) {
					yMax = height-1;
				}
				Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, polynomial, data);
				results.add(pool.submit(job));
			}
			
			for(Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			observer.acceptResult(data, (short)(polynomial.toComplexPolynom().order()+1), requestNo);
		}
	}
	
	/**
	 * A factory of threads with the flag 'daemon' set to true.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	}
	
	/**
	 * This method reads complex numbers from the console and stores them into a list.
	 * 
	 * @return A list of complex numbers.
	 */
	public static List<Complex> readData() {
		List<Complex> roots = new LinkedList<>();
		Scanner sc = new Scanner(System.in);
		int i = 0;
		System.out.print("Root " + i + "> ");
		while(sc.hasNext()) {
			String str = sc.nextLine();
			if(str.equals("done")) {
				break;
			}
			
			char[] input = str.toCharArray();
			int index = 0;
			StringBuilder sb = new StringBuilder();
			if(str.startsWith("i") || str.startsWith("-i")) {
				boolean negative = false;
				if(input[0] == '-') {
					index++;
					negative = true;
				}
				index++;
				while(index < input.length) {
					sb.append(input[index]);
					index++;
				}
				double imag;
				try {
					if(negative && input.length == 2 || !negative && input.length == 1) {
						imag = 1;
					} else {
						imag = Double.parseDouble(sb.toString());
					}
					
					if(imag < 0) {
						System.out.println("Cannot parse the given input.");
						i--;
					} else {
						if(negative) {
							imag = -imag;
						}
						roots.add(new Complex(0, imag));
					}
					
				} catch(Exception e) {
					System.out.println("Cannot parse the given input.");
					i--;
				}
				
			} else if(!str.contains("i")) {
				double real;
				try {
					real = Double.parseDouble(str);
					roots.add(new Complex(real, 0));			
				} catch(Exception e) {
					System.out.println("Cannot parse the given input.");
					i--;
				}
				
			} else {
				try {
					boolean realNegative = false;
					if(input[0] == '-') {
						index++;
						realNegative = true;
					}
					while(input[index] != ' ' && input[index] != '-' && 
							input[index] != '+' && index < input.length) {
						sb.append(input[index]);
						index++;
					}
					String realString = sb.toString();
					sb.setLength(0);
					
					while(input[index] == ' ') {
						index++;
					}
					
					boolean imagNegative = false;
					if(input[index] == '-') {
						index++;
						imagNegative = true;
					} else if(input[index] == '+') {
						index++;
					} else {
						throw new IllegalArgumentException();
					}
					
					while(input[index] == ' ') {
						index++;
					}
					
					if(input[index] == 'i') {
						index++;
					} else {
						throw new IllegalArgumentException();
					}
					
					while(index < input.length) {
						sb.append(input[index]);
						index++;
					}
					String imagString = sb.toString();
					
					double real = Double.parseDouble(realString);
					double imag;
					if(imagString.length() == 0) {
						imag = 1;
					} else {
						imag = Double.parseDouble(imagString);
					}
					
					if(real < 0 || imag < 0) {
						throw new IllegalArgumentException();
					}
					
					if(realNegative) {
						real = -real;
					}
					if(imagNegative) {
						imag = -imag;
					}
					roots.add(new Complex(real, imag));
				} catch(Exception e) {
					System.out.println("Cannot parse the given input.");
					i--;
				}
			}
			
			i++;
			System.out.print("Root " + i + "> ");
		}
		sc.close();
		
		return roots;
	}
}
