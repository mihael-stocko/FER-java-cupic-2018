package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This worker takes two numbers from the given context and calculates their sum.
 * If the parameters retrieved are not numbers, default values are used.
 * The result is set as a temporary parameter and then the calc script is called. 
 * 
 * @author Mihael Stočko
 *
 */
public class SumWorker implements IWebWorker {

	/**
	 * Default value for the first operand.
	 */
	private static final int defaultA = 1;
	
	/**
	 * Default value for the second operand.
	 */
	private static final int defaultB = 2;
	
	/**
	 * Name of the first operand.
	 */
	private static final String param1 = "a";
	
	/**
	 * Name of the second operand.
	 */
	private static final String param2 = "b";
	
	/**
	 * Name of the result.
	 */
	private static final String res = "sum";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		Integer a, b, sum;
		
		String paramA = context.getParameter(param1);
		if(paramA == null) {
			a = defaultA;
		} else {
			try {
				a = Integer.parseInt(paramA);
			} catch(NumberFormatException e) {
				a = defaultA;
			}
		}
		
		String paramB = context.getParameter(param2);
		if(paramB == null) {
			b = defaultB;
		} else {
			try {
				b = Integer.parseInt(paramB);
			} catch(NumberFormatException e) {
				b = defaultB;
			}
		}
		
		sum = a + b;
		context.setTemporaryParameter(param1, a.toString());
		context.setTemporaryParameter(param2, b.toString());
		context.setTemporaryParameter(res, sum.toString());
		
		context.getDispatcher().dispatchRequest("private/calc.smscr");
	}
}
