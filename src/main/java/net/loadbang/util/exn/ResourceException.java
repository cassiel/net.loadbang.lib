//	$Id$
//	$Source$

package net.loadbang.util.exn;

public class ResourceException extends Exception {
	public ResourceException(String message, Exception cause) {
		super(message, cause);
	}
	
	public ResourceException(String message) {
		this(message, null);
	}
}
