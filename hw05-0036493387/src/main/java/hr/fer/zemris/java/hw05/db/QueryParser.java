package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.LinkedList;

/**
 * A simple parser.
 * 
 * @author Mihael Stočko
 *
 */
public class QueryParser {
	
	/**
	 * Constant string firstName
	 */
	private static final String first = "firstName";
	
	/**
	 * Constant string lastName
	 */
	private static final String last = "lastName";
	
	/**
	 * Constant string AND
	 */
	private static final String and = "AND";
	
	/**
	 * List of the generated tokens
	 */
	private List<QueryToken> tokenList = new LinkedList<>();
	
	/**
	 * List of conditional expressions generated using tokens
	 */
	private List<ConditionalExpression> expressionList = new LinkedList<>();
	
	/**
	 * Constructor. Takes a string as an input and decomposes it into conditional expressions.
	 * 
	 * @param input
	 */
	public QueryParser(String input) {
		tokenList = tokenise(input);
		expressionList = createExpressions(tokenList);
	}
	
	/**
	 * Decomposes the input string into tokens using the {@link QueryLexer}.
	 * 
	 * @param input
	 * @return
	 */
	private List<QueryToken> tokenise(String input) {
		QueryToken token;
		QueryLexer lexer = new QueryLexer(input);
		List<QueryToken> list = new LinkedList<>();
		while(true) {
			token = lexer.nextToken();
			if(token.getType().equals(QueryTokenType.EOF)) {
				break;
			}
			list.add(token);
		}
		return list;
		
	}
	
	/**
	 * Creates a list of {@link ConditionalExpression} object using the tokens.
	 * 
	 * @param list
	 * @return
	 */
	private List<ConditionalExpression> createExpressions(List<QueryToken> list) {
		List<ConditionalExpression> newList = new LinkedList<>();
		
		if((list.size()+1) % 4 != 0) {
			throw new IllegalArgumentException("Received tokens are not in the required format.");
		}
		
		QueryToken token;
		int i = 0;
		
		while(true) {
			token = list.get(i);
			if(token.getType() != QueryTokenType.VARIABLE) {
				throw new IllegalArgumentException("VARIABLE expected, " + token.getType() + " got.");
			}
			IFieldValueGetter getter = getTheGetter(token);
			
			i++;
			token = list.get(i);
			if(token.getType() != QueryTokenType.OPERATOR) {
				throw new IllegalArgumentException("OPERATOR expected, " + token.getType() + " got.");
			}
			IComparisonOperator operator = getTheOperator(token);
			
			i++;
			token = list.get(i);
			if(token.getType() != QueryTokenType.STRING) {
				throw new IllegalArgumentException("STRING expected, " + token.getType() + " got.");
			}
			String literal = (String)token.getValue();
			
			ConditionalExpression exp = new ConditionalExpression(getter, literal, operator);
			newList.add(exp);
			
			i++;
			if(i == list.size()) {
				break;
			}
			token = list.get(i);
			if(!(token.getType() == QueryTokenType.OPERATOR && token.getValue().equals(and))) {
				throw new IllegalArgumentException("AND expected.");
			}
			i++;
		}
		
		return newList;
	}
	
	/**
	 * Returns an appropriate FieldValueGetter depending on the token provided as input.
	 * 
	 * @param token
	 * @return
	 */
	private IFieldValueGetter getTheGetter(QueryToken token) {
		if(token.getValue().equals(first)) {
			return FieldValueGetters.FIRST_NAME;
		} else if(token.getValue().equals(last)) {
			return FieldValueGetters.LAST_NAME;
		} else {
			return FieldValueGetters.JMBAG;
		}
	}
	
	/**
	 * Returns an appropriate ComparisonOperator depending on the token provided as input.
	 * 
	 * @param token
	 * @return
	 */
	private IComparisonOperator getTheOperator(QueryToken token) {
		if(token.getValue().equals("<")) {
			return ComparisonOperators.LESS;
		} else if(token.getValue().equals("<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		} else if(token.getValue().equals(">")) {
			return ComparisonOperators.GREATER;
		} else if(token.getValue().equals(">=")) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		} else if(token.getValue().equals("=")) {
			return ComparisonOperators.EQUALS;
		} else if(token.getValue().equals("!=")) {
			return ComparisonOperators.NOT_EQUALS;
		} else {
			return ComparisonOperators.LIKE;
		}
	}
	
	/**
	 * Checks if the parsed line is a direct query.
	 * 
	 * @return <code>true</code> if the parsed line is a direct query, <code>false</code> otherwise.
	 */
	public boolean isDirectQuery() {
		return expressionList.size() == 1 && 
				expressionList.get(0).getComparisonOperator() == ComparisonOperators.EQUALS &&
				expressionList.get(0).getFieldGetter() == FieldValueGetters.JMBAG;
	}
	
	/**
	 * Returns the JMBAG of the direct query.
	 * 
	 * @return JMBAG
	 * @throws IllegalStateException if the query is not a direct query,
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("Query is not a direct query.");
		}
		
		return expressionList.get(0).getStringLiteral();
	}
	
	/**
	 * Returns the query as a list of {@link ConditionalExpression} objects.
	 * 
	 * @return
	 */
	public List<ConditionalExpression> getQuery() {
		return expressionList;
	}
}
