//	$Id$
//	$Source$

package net.loadbang.util.exn;

public class MaxXMLPropertiesException extends Exception {
	public MaxXMLPropertiesException(String message, Exception cause) {
		super(message, cause);
	}
	
	public MaxXMLPropertiesException(String message) {
		this(message, null);
	}
}
