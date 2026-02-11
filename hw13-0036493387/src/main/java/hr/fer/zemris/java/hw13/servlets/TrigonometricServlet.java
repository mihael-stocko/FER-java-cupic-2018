package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet takes two parameters: a and b.
 * a - starting angle (default = 0)
 * b - ending angle (default = 360)
 * It makes a list of {@link Trigonometric} objects ranging from a to b, or vice versa if
 * b < a. It sets the list as an attribute and then calls trig.jsp which should make a table
 * from the list.
 * 
 * @author Mihael Stočko
 *
 */
public class TrigonometricServlet extends HttpServlet {

public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param1 = req.getParameter("a");
		String param2 = req.getParameter("b");
		int a, b;
		a = 0;
		b = 360;
		if(param1 != null) {
			try {
				a = Integer.parseInt(param1);
			} catch(NumberFormatException e) {
				
			}
		}
			
		if(param2 != null) {
			try {
				b = Integer.parseInt(param2);
			} catch(NumberFormatException e) {
				
			}
		}
		
		if(a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		if(b > a + 720) {
			b = a + 720;
		}
		
		List<Trigonometric> list = new ArrayList<>();
		for(int i = a; i <= b; i++) {
			list.add(new Trigonometric(i));
		}
		
		req.setAttribute("trig", list);
		req.getRequestDispatcher("/WEB-INF/pages/trig.jsp").forward(req, resp);
	}
	
	/**
	 * A class that holds an angle in degrees and its sine and cosine values.
	 * 
	 * @author Mihael Stočko
	 *
	 */
	public static class Trigonometric {
		/**
		 * Angle
		 */
		private int fi;
		
		/**
		 * sin(fi)
		 */
		private double sin;
		
		/**
		 * cos(fi)
		 */
		private double cos;
		
		/**
		 * Constructor
		 * @param fi Angle
		 */
		public Trigonometric(int fi) {
			super();
			this.fi = fi;
			this.sin = Math.sin(fi/360.0*2*Math.PI);
			this.cos = Math.cos(fi/360.0*2*Math.PI);
		}

		/**
		 * Getter for angle
		 */
		public int getFi() {
			return fi;
		}

		/**
		 * Setter for angle
		 */
		public void setFi(int fi) {
			this.fi = fi;
		}

		/**
		 * Getter for sin
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * Setter for sin
		 */
		public void setSin(double sin) {
			this.sin = sin;
		}

		/**
		 * Getter for cos
		 */
		public double getCos() {
			return cos;
		}

		/**
		 * Setter for cos
		 */
		public void setCos(double cos) {
			this.cos = cos;
		}
	}
}
