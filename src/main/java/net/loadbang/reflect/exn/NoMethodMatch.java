//	$Id$
//	$Source$

package net.loadbang.reflect.exn;

/**	Method not found when trying to dispatch via method name. */

public class NoMethodMatch extends Exception {
	private String itsMethodName;

	public NoMethodMatch(String methodName) {
		itsMethodName = methodName;
	}
	
	@Override public String toString() {
		return "no matching method arguments found: " + itsMethodName;
	}
}
