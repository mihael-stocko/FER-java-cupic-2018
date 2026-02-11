package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class models a conditional expression.
 * 
 * @author Mihael Stočko
 *
 */
public class ConditionalExpression {

	/**
	 * Field value getter
	 */
	IFieldValueGetter fieldGetter;
	
	/**
	 * String literal
	 */
	String stringLiteral;
	
	/**
	 * Comparison operator
	 */
	IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor
	 * 
	 * @param getter
	 * @param literal
	 * @param operator
	 * @throws NullPointerException
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		Objects.requireNonNull(getter, "getter cannot be null.");
		Objects.requireNonNull(literal, "literal cannot be null.");
		Objects.requireNonNull(operator, "operator cannot be null.");
		
		this.fieldGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}

	/**
	 * Getter for fieldGetter
	 * 
	 * @return
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for stringLiteral
	 * 
	 * @return
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for comparisonOperator
	 * 
	 * @return
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
