/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

import java.util.Date;

/** Common superclass for loggers: knows about indenting, formatting
    and stuff like that. Used by ConsoleLogger and ServletLogger.

    @see dandelion.common.ConsoleLogger
    @see dandelion.common.ServletLogger */

abstract public class FormattingLogger extends Logger {
    /** Fixed length of padding field. */
    static final private int PADLENGTH = 8;

    /** Tag prefix to use in messages. */
    private String itsPrefix;

    /** Load-time flag: are we logging? (as debug - we always log
     for INFO and above.) */

    private boolean itsLogging;

    /** Private semaphore to make sure our two-line messages don't
	get intermixed. */

    private static Object theSemaphore = new Object();

    /** Create a formatting logger with this prefix. */

    protected FormattingLogger(String prefix)
    {
	String pad = "";
	for (int i = 0; i < PADLENGTH; i++) { pad += " "; }
	itsPrefix = (prefix + pad).substring(0, PADLENGTH);

	itsLogging =
	    Manifest.TRUE.equals(
		Properties.getString(Manifest.DEBUG_PROPERTYFILE, prefix, Manifest.FALSE)
	    );
    }

    /** It's sometimes useful to know if we're debugging (so as to
	set up other printing). */

    @Override
	public boolean isDebugging()
    {
	return itsLogging;
    }

    /** Log a message with the funny prefix. NOTE: the prefix has these
	decorated brackets for a reason: the testset scripts know to
	ignore them. We log if the logging for this facility is on in
	dandelion.properties OR the level is something more important
	than DEBUG. */

    @Override
	public void log(String level, String message)
    {
	if (   itsLogging
	    || !level.equals(Logger.DEBUG)
	   ) {
	    synchronized (theSemaphore) {
		emit("[|" + itsPrefix + "/" + level
		     + "  --  " + new Date() + "|]"
		    );
		emit("    " + message);
	    }
	}
    }
    
    /** Print out log with exception. (MEMO: we should try and split the exception text up
        into lines, otherwise we're emitting lines with linebreaks.) */
    
    @Override
	public void log(String level, String message, Exception exn)
    {
        log(level, message + ": " + exn.toString());
    }

    /** Print out a formatted line. */

    abstract protected void emit(String line);
}
