//	$Id$
//	$Source$

package net.loadbang.reflect.exn;

/**	General wrapper for dispatcher errors. */

public class Dispatch extends Exception {
	private String itsHint;
	private Exception itsReason;

	public Dispatch(String hint, Exception reason) {
		itsHint = hint;
		itsReason = reason;
	}
	
	@Override public String toString() {
		return "dispatch: " + itsHint;
	}
	
	public Exception getReason() {
		return itsReason;
	}
}
