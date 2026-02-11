package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * This class is a filter. It filters a list of {@link StudentRecord} objects using a provided
 * criterion.
 * 
 * @author Mihael Stočko
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * Initial list
	 */
	List<ConditionalExpression> list;
	
	/**
	 * Constructor
	 * 
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		Objects.requireNonNull(list, "QueryFilter does not accept null as an argument.");
		
		this.list = list;
	}

	/**
	 * This method returns <code>true</code> if the record satisfies the condition.
	 * Returns <code>false</code> otherwise.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression ce : list) {
			if(!ce.getComparisonOperator().satisfied(ce.getFieldGetter().get(record), ce.getStringLiteral())) {
				return false;
			}
		}
		
		return true;
	}
}
