/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

import java.io.*;

/** Logging package com.workthing.using console. Works for <CODE>httpd -X</CODE>, but
    not for a properly detached server (in which case, see
    {@link ServletLogger ServletLogger}). */

public class ConsoleLogger extends FormattingLogger {
    /** Fixed static console output writer. */
    static private PrintWriter theWriter = new PrintWriter(System.out, true);

    /** Pass the prefix tag string to {@link FormattingLogger
        FormattingLogger}. */

    ConsoleLogger(String prefix)
    {
	super(prefix);
    }

    /** Log a message to the "console" (whatever that might mean). */

    @Override
	protected void emit(String line)
    {
	theWriter.println(line);
	theWriter.flush();
    }
}
