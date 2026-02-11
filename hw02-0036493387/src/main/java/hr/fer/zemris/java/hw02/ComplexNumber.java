package hr.fer.zemris.java.hw02;

/**
 * A class that represents an immutable complex number. Supports addition, subtraction,
 * multiplication, division, exponentiation and taking roots.
 * 
 * @author Mihael Stočko
 *
 */
public class ComplexNumber {
	
	/**
	 * Real part of the number.
	 */
	private double real;
	
	/**
	 * Imaginary part of the number.
	 */
	private double imaginary;
	
	/**
	 * Constructor.
	 * 
	 * @param real Real part of the number.
	 * @param imaginary Imaginary part of the number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Creates a complex number from the real part only.
	 * 
	 * @param real Real part of the number.
	 * @return Created complex number.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Creates a complex number from the imaginary part only.
	 * 
	 * @param imaginary Imaginary part of the number.
	 * @return Created complex number.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a complex number from the magnitude and the angle.
	 * 
	 * @param magnitude Magnitude of the complex number.
	 * @param angle Angle of the complex number.
	 * @return Created complex number.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude < 0) {
			throw new IllegalArgumentException("Magnitude cannot be less than zero, was " + magnitude + ".");
		}
		
		return new ComplexNumber(Math.cos(angle)*magnitude, Math.sin(angle)*magnitude);
	}
	
	/**
	 * Parses the provided string into a complex number.
	 * 
	 * @param s String to be parsed.
	 * @return Created complex number.
	 * @throws IllegalArgumentException
	 */
	public static ComplexNumber parse(String s) {
		double real = 0;
		double imaginary = 0;
		
		try {
			real = Double.parseDouble(s);
		} catch(Exception e) {
			try {
				StringBuilder sb = new StringBuilder();
				
				int i = 0;
				do {
					sb.append(s.charAt(i));
					i++;
				} while(s.charAt(i) != '+' && s.charAt(i) != '-' && s.charAt(i) != 'i');
				
				if(s.charAt(i) == 'i') {
					imaginary = Double.parseDouble(sb.toString());
					return new ComplexNumber(real, imaginary);
				}
				
				real = Double.parseDouble(sb.toString());
				sb = new StringBuilder();
				do {
					sb.append(s.charAt(i));
					i++;
				} while(s.charAt(i) != 'i');
				imaginary = Double.parseDouble(sb.toString());
			} catch(Exception e2) {
				throw new IllegalArgumentException("Input string in wrong format, was " + s);
			}
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Getter for the real part of the number.
	 * 
	 * @return Real part of the complex number.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Getter for the imaginary part of the number.
	 * 
	 * @return Imaginary part of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns the magnitude of the complex number.
	 * 
	 * @return Magnitude of the complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}
	
	/**
	 * Returns the angle of the complex number.
	 * 
	 * @return Angle of the complex number.
	 */
	public double getAngle() {
		if(Math.abs(imaginary) < Constants.epsilon) {
			if(real > 0) {
				return 0;
			} else {
				return Math.PI;
			}
		}
		
		if(Math.abs(real) < Constants.epsilon) {
			if(imaginary > 0) {
				return Math.PI/2;
			} else {
				return Math.PI*3/2;
			}
		}
		
		double result = Math.atan(imaginary / real);
		if(result > 0) {
			if(real > 0) {
				return result;
			} else {
				return result + Math.PI;
			}
		} else {
			if(imaginary > 0) {
				return result + Math.PI;
			} else {
				return result + Math.PI*2;
			}
		}
	}
	
	/**
	 * Adds itself to the complex number from the argument.
	 * 
	 * @param c Operand for the addition.
	 * @return A new complex number, sum of the two used.
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Argument to add method cannot be null.");
		}
		
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}
	
	/**
	 * Subtracts the argument from itself.
	 * 
	 * @param c Operand for the subtraction.
	 * @return A new complex number, difference of the two used.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Argument to subtract method cannot be null.");
		}
		
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}
	
	/**
	 * Multiplies itself with the complex number from the argument.
	 * 
	 * @param c Operand for the multiplication.
	 * @return A new complex number, product of the two used.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Argument to multiply method cannot be null.");
		}
		
		return new ComplexNumber(real*c.real - imaginary*c.imaginary, real*c.imaginary + imaginary*c.real);
	}

	/**
	 * Divides itself with the complex number from the argument.
	 * 
	 * @param c Operand for the division.
	 * @return A new complex number, quotient of the two used.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Argument to divide method cannot be null.");
		}
		
		ComplexNumber complexConjugate = new ComplexNumber(c.real, -c.imaginary);
		ComplexNumber dividend = this.mul(complexConjugate);
		ComplexNumber divisor = c.mul(complexConjugate);
		
		return new ComplexNumber(dividend.real/divisor.real, dividend.imaginary/divisor.real);
	}
	
	/**
	 * Raises itself to the power of the argument.
	 * 
	 * @param n Exponent
	 * @return A new complex number, result of the exponentiation.
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Exponent cannot be smaller than zero, was " + n + ".");
		}
		
		if(n == 0) {
			return new ComplexNumber(1, 0);
		} else {
			ComplexNumber temp = new ComplexNumber(1, 0);
			for(int i = 0; i < n; ++i) {
				temp = temp.mul(this);
			}
			return temp;
		}
	}
	
	/**
	 * Takes the n-th root from the complex number.
	 * 
	 * @param n Exponent
	 * @return An array of complex numbers, roots of the original number.
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Argument to root function cannot be less than one, was " + n + ".");
		}
		
		ComplexNumber[] complexNumberArray = new ComplexNumber[n];
		
		for(int i = 0; i < n; ++i) {
			double magnitude = this.getMagnitude();
			magnitude = Math.pow(magnitude, 1.0/n);
			ComplexNumber newComplexNumber = new ComplexNumber(magnitude*Math.cos((this.getAngle()+i*2*Math.PI)/n), magnitude*Math.sin((this.getAngle()+i*2*Math.PI)/n));
			complexNumberArray[i] = newComplexNumber;
		}
		
		return complexNumberArray;
	}
	
	/**
	 * Converts the number to a string.
	 * 
	 * @return A string representing the complex number.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(this.real == 0) {
			if(this.imaginary == 0) {
				sb.append(0);
			} else {
				sb.append(this.imaginary);
				sb.append("i");
			}
		} else {
			sb.append(this.real);
			if(this.imaginary != 0) {
				if(this.imaginary > 0) {
					sb.append("+");
				}
				sb.append(this.imaginary);
				sb.append("i");
			}
		}		
		
		return sb.toString();
	}
}