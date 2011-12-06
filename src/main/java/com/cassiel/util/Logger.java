/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

/** Interface for logging lines to an output stream or log. */

abstract public class Logger {
    /** Status code: debug (default). Messages under <CODE>DEBUG</CODE>
	will be suppressed by default (unless enabled as a property);
	all other levels will be printed regardless of property. */
    public static final String DEBUG  = "DEBUG";

    /** Status code: information. Printed by default, so keep them
	sparse. */

    public static final String INFO  = "INFO";

    /** Status code: warning. */
    public static final String WARN  = "WARN";

    /** Status code: error. */
    public static final String ERROR = "ERROR";

    /** Log a message, with <CODE>INFO</CODE> severity. */
    public void log(String message)
    {
	log(DEBUG, message);
    }

    abstract public boolean isDebugging();

    /** Log a message with the specified severity. */
    abstract public void log(String level, String message);
    
    /** Log a message with severity and exception. */
    abstract public void log(String level, String message, Exception exn);
}
