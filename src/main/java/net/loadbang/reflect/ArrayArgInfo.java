//	$Id$
//	$Source$

package net.loadbang.reflect;

import java.lang.reflect.Array;
import com.cycling74.max.Atom;
import com.cassiel.util.INTERNAL;

/**	Information about a method which takes an array as argument. */

public class ArrayArgInfo extends MethodArgInfo {
	/**	Formal type (inside array). */
	private Class itsFormal;
	
	/**	Constructor. */
	ArrayArgInfo(Class formal) {
		itsFormal = formal;
	}

	/**	Score these argument types against our array type. */
	@Override int scoreActualArgs(ScoreTypes scorer, Class[] actuals) {
		int result = ScoreTypes.EXACT;

		if (actuals.length == 0) {
			return ScoreTypes.WIDEN_ARRAY;	//	That's rather arbitrary: an empty actual
											//	argument fuzzy-matches any array type. (We
											//	shouldn't exact-match: that might cause us
											//	to have multiple exacts, which is nasty, and
											//	would score equal with a discrete nothing.)
		} else {
			for (Class cl: actuals) {
				int score = scorer.score(cl, itsFormal);
				result = Math.min(result, score);	//	We want the weakest match.
			}
			
			//	An array scores slightly less than discrete args.
			return scorer.arrayScore(result);
		}
	}
	
	/**	Build an array of objects representing the values of args, prior to invoke().
	 	The method takes an array of int/float/String. We only allow exact matching
	 	of types for arrays, and itsFormal gives us the class (possibly primitive) of
	 	the type to build.

		@param args the actual arguments.
	 	@return an array to pass to invoke().
	 */

	@Override public Object[] buildActualValues(Atom args[]) {
		Object array;

		if (args.length == 0) {
			array = Array.newInstance(itsFormal, 0);
		} else if (itsFormal == Integer.TYPE) {
			array = Atom.toInt(args);
		} else if (itsFormal == Float.TYPE) {
			array = Atom.toFloat(args);
		} else if (itsFormal == String.class) {
			array = Atom.toString(args);
		} else {
			throw new INTERNAL("ArrayArgInfo.buildActualValues(" + itsFormal + ")");
		}
		
		return new Object[] { array };
	}
	
	@Override public String toString() {
		return "Array: " + itsFormal;
	}
}
