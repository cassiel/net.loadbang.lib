/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

/** Some stuff has to be totally global. For example, it is very
    difficult to get a global logging system in a servlet environment,
    so the best we can do is use global placeholders like
    these. Clearly, they need to be planted before use. Clearly also,
    the fewer the better. */

public class GlobalHooks
{
    /** Set of logging objects. By default, they'll be console
	loggers, but the servlet superclass {@link
	dandelion.upload.servlet.abs.SimplePage SimplePage} flips them
	to a set of servlet loggers instead. */

    static private Loggers itsLoggers = new ConsoleLoggers();
				// Use the console loggers by default;
				// our servlet heirarchy replaces these.

    /** Plant a set of loggers. */
    static public void plantLoggers(Loggers loggers)
    {
	itsLoggers = loggers;
    }

    /** Retrieve the loggers. */
    static public Loggers loggers()
    {
	return itsLoggers;
    }
}
