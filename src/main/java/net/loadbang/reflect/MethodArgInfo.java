//	$Id$
//	$Source$

package net.loadbang.reflect;

import com.cassiel.util.INTERNAL;
import com.cycling74.max.Atom;

/**	Information about the (formal) arguments to a method overloading. */

/**
 * @author nick
 *
 */
abstract public class MethodArgInfo {
	/**	Give a score for a set of actual argument types.

	 	@param scorer the scoring object.
	 	@param actuals list of classes of actual arguments. */
	abstract int scoreActualArgs(ScoreTypes scorer, Class[] actuals);
	
	/**	Return the Java object corresponding to an atom. (We need this here because
	 	the way we build actual arguments for invocation depends on whether the method
	 	takes an array or not.) */
	protected Object buildActualValue(Atom a) {
		if (a.isInt()) {
			return new Integer(a.getInt());
		} else if (a.isFloat()) {
			return new Float(a.getFloat());
		} else if (a.isString()) {
			return new String(a.getString());
		} else {
			throw new INTERNAL("Dispatcher.buildActualValue: " + a);
		}
	}

	/** Build an array of objects ready to pass into invoke(). It's pretty straightforward
	 	for non-array arguments,  but messy when the method takes an array, probably of
	 	primitive type.
	 
	 	@param args the actual arguments to convert.
	 	@return an array of Objects suitable for passing to invoke().
	 */
	
	abstract public Object[] buildActualValues(Atom args[]);
}
