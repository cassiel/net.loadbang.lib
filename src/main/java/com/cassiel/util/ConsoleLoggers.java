/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

/** A set of logging objects, categorised by debugging "genre".

    The tags have a maximum length (otherwise they'll be truncated): see
    {@link ConsoleLogger ConsoleLogger}.

    <B>Note</B>: the prefix parameters must match the property names
    in <CODE>dandelion.properties</CODE>, in order for selective
    debugging to work.

    @see ConsoleLogger */

public class ConsoleLoggers extends Loggers
{
    public ConsoleLoggers()
    {
	config		= new ConsoleLogger("CONFIG");
	session		= new ConsoleLogger("SESSION");
	instancing	= new ConsoleLogger("INSTANCE");
	persistence	= new ConsoleLogger("PERSIST");
	db		= new ConsoleLogger("DB");
	sql		= new ConsoleLogger("SQL");
	thread		= new ConsoleLogger("THREAD");
	network		= new ConsoleLogger("NETWORK");
	misc		= new ConsoleLogger("MISC");
    }
}
