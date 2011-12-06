//	$Id$
//	$Source$

package net.loadbang.reflect;

import net.loadbang.util.TwoDHashMap;

import com.cassiel.util.INTERNAL;

/**	Score the match between an actual argument type and a (possibly overloaded) formal
 	parameter.
 */

public class ScoreTypes {
	static final int EXACT    	  = 4;
	static final int EXACT_ARRAY    = 3;
	static final int WIDEN     	  = 2;
	static final int WIDEN_ARRAY	  = 1;	//	Only used for empty actuals vs. array formal.
	static final int MISMATCH		  = 0;
	
	/**	Dictionary mapping actual * formal -> score. */
	private TwoDHashMap<Class, Class, Integer> itsMap;
	
	/**	Constructor: builds an internal map from actual * formal param to score
	 	(either EXACT or WIDEN). */
	public ScoreTypes() {
		itsMap = new TwoDHashMap<Class, Class, Integer>();
		
		addExact(Integer.TYPE);
		addExact(Float.TYPE);
		addExact(String.class);
		
		add(Integer.TYPE, Float.TYPE, WIDEN);
	}

	/**	Add a formal-to-actual score. */
	private void add(Class formal, Class actual, int score) {
		itsMap.put(formal, actual, score);
	}
	
	/**	Add an identity map. */
	private void addExact(Class cl) {
		add(cl, cl, EXACT);
	}

	/**	Score an actual type against a formal type. */
	public int score(Class actual, Class formal) {
		Integer score00 = itsMap.get00(actual, formal);
		
		//System.out.println("score of A=" + actual + " F=" + formal + " -> " + score00);
		
		if (score00 == null) {
			return MISMATCH;
		} else {
			return score00;
		}
	}
	
	/**	Determine a score for a formal array argument, given that we've matched all the
	 	actuals. */

	public int arrayScore(int discreteScore) {
		switch (discreteScore) {
			case EXACT:
				return EXACT_ARRAY;
			
			//	It appears that the auto-widening doesn't work for arrays: if a method
			//	takes float[] we can't invoke it with int[]. So, we don't allow any
			//	kind of fuzzy match for arrays.
			case WIDEN:
				return WIDEN_ARRAY;
			
			default:
				throw new INTERNAL("ScoreTypes.arrayScore(" + discreteScore + ")");
		}
	}
}
