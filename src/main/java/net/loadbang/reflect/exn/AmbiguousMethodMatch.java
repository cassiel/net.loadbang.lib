//	$Id$
//	$Source$

package net.loadbang.reflect.exn;

/**	Method not found when trying to dispatch via method name. */

public class AmbiguousMethodMatch extends Exception {
	private String itsMethodName;

	public AmbiguousMethodMatch(String methodName) {
		itsMethodName = methodName;
	}
	
	@Override public String toString() {
		return "ambiguous method arguments found: " + itsMethodName;
	}
}
