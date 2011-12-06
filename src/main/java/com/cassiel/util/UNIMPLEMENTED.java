/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

public class UNIMPLEMENTED extends Error {
	private String itsReason;

    public UNIMPLEMENTED(String reason)
    {
	itsReason = reason;
    }

    @Override
	public String toString()
    {
	return "UNIMPLEMENTED [" + itsReason + "]";
    }
}
