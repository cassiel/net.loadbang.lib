//	$Id$
//	$Source$

package net.loadbang.reflect.exn;

/**	Method not found when trying to dispatch via method name. */

public class MethodNotFound extends Exception {
	private String itsMethodName;

	public MethodNotFound(String methodName) {
		itsMethodName = methodName;
	}
	
	@Override public String toString() {
		return "method not found: " + itsMethodName;
	}
}
