package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A collection of classes that implement {@link IComparisonOperator}.
 * 
 * @author Mihael Stočko
 *
 */
public class ComparisonOperators {

	/**
	 * Overrides the satisfied method so that it checks if the first string is less than the second.
	 * Uses {@link String#compareTo(String)}
	 */
	public static final IComparisonOperator LESS = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "value1 cannot be null.");
			Objects.requireNonNull(value2, "value2 cannot be null.");
			
			return value1.compareTo(value2) < 0;
		}
	};
	
	/**
	 * Overrides the satisfied method so that it checks if the first string is less or equal to the second.
	 * Uses {@link String#compareTo(String)}
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "value1 cannot be null.");
			Objects.requireNonNull(value2, "value2 cannot be null.");
			
			return value1.compareTo(value2) <= 0;
		}
	};
	
	/**
	 * Overrides the satisfied method so that it checks if the first string is greater than the second.
	 * Uses {@link String#compareTo(String)}
	 */
	public static final IComparisonOperator GREATER = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "value1 cannot be null.");
			Objects.requireNonNull(value2, "value2 cannot be null.");
			
			return value1.compareTo(value2) > 0;
		}
	};
	
	/**
	 * Overrides the satisfied method so that it checks if the first string is greter or equal to the second.
	 * Uses {@link String#compareTo(String)}
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "value1 cannot be null.");
			Objects.requireNonNull(value2, "value2 cannot be null.");
			
			return value1.compareTo(value2) >= 0;
		}
	};
	
	/**
	 * Overrides the satisfied method so that it checks if the first string is equal to the second.
	 * Uses {@link String#compareTo(String)}
	 */
	public static final IComparisonOperator EQUALS = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "value1 cannot be null.");
			Objects.requireNonNull(value2, "value2 cannot be null.");
			
			return value1.compareTo(value2) == 0;
		}
	};
	
	/**
	 * Overrides the satisfied method so that it checks if the first string is not equal to the second.
	 * Uses {@link String#compareTo(String)}
	 */
	public static final IComparisonOperator NOT_EQUALS = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "value1 cannot be null.");
			Objects.requireNonNull(value2, "value2 cannot be null.");
			
			return value1.compareTo(value2) != 0;
		}
	};
	
	/**
	 * Overrides the satisfied method so that it checks if the first string corresponds to the pattern
	 * provided as the second string.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "value1 cannot be null.");
			Objects.requireNonNull(value2, "value2 cannot be null.");
			
			String[] s = value2.split("\\*");
			if(s.length == 2) {
				return value1.endsWith(s[1]) && value1.startsWith(s[0]) && 
						value1.length() >= s[0].length() + s[1].length();
			} else if(s.length == 1) {
				if(value2.charAt(0) == '*') {
					return value1.endsWith(s[0]) && value1.length() >= s[0].length();
				} else {
					return value1.startsWith(s[0]) && value1.length() >= s[0].length();
				}
			} else {
				throw new IllegalArgumentException("Operator LIKE can accept only one wildcard.");
			}
		}
	};
}
