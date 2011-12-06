/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

/** Internal error. (Error rather than Exception, hence unchecked.)
    Arguably we should use {@link FAILURE FAILURE} instead, and
    handle it properly.) */

public class INTERNAL extends Error {
	public INTERNAL(String reason) {
		super(reason);
	}

	public INTERNAL(Exception exn) {
		super("INTERNAL (unspecified)", exn);
	}
	
	public INTERNAL(String message, Exception exn) {
		super(message, exn);
	}
}
