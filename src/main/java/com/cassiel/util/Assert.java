/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

/** Assertion class, used for program invariants. */

public class Assert
{
    /** Fail with internal error if condition is not true. 

	@param message printed if condition is false.
	@param condition causes internal error if false.

	@see INTERNAL

     */
    static public void do_assert(String message,
				 boolean condition
				)
    {
	if (!condition) {
	    throw new INTERNAL("ASSERT: " + message);
	}
    }
}
