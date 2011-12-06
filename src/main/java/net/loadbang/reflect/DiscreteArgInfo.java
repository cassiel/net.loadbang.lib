//	$Id$
//	$Source$

package net.loadbang.reflect;

import com.cycling74.max.Atom;

public class DiscreteArgInfo extends MethodArgInfo {
	/**	List of formal types. */
	private Class[] itsFormals;
	
	/**	Constructor. */
	DiscreteArgInfo(Class[] formals) {
		itsFormals = formals;
	}

	/**	Calculate a score against a list of actual parameter types. */
	@Override int scoreActualArgs(ScoreTypes scorer, Class[] actuals) {
		int result = ScoreTypes.EXACT;

		if (actuals.length == itsFormals.length) {
			for (int i = 0; i < actuals.length; i++) {
				Class cl = actuals[i];
				int score = scorer.score(cl, itsFormals[i]);
				result = Math.min(result, score);	//	We want the weakest match.
			}
			
			return result;
		} else {			//	Length mismatch.
			return ScoreTypes.MISMATCH;
		}
	}
	
	/**	Build an array of objects representing the values of args.
	 
	 	@param args the actual arguments to construct for invoke().
	 */
	
	@Override public Object[] buildActualValues(Atom args[]) {
		Object[] result = new Object[args.length];
		
		for (int i = 0; i < args.length; i++) {
			result[i] = buildActualValue(args[i]);
		}
		
		return result;
	}
	
	@Override public String toString() {
		StringBuffer buffer = new StringBuffer("Discrete: [");
		
		for (Class cl: itsFormals) {
			buffer.append(" " + cl.toString());
		}
		
		buffer.append(" ]");
		return buffer.toString();
	}
}
