/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

/** Interface to a set of logging objects, categorised by debugging "genre".

    The tags have a maximum length (otherwise they'll be truncated): see
    {@link FormattingLogger FormattingLogger}.

    @see dandelion.common.FormattingLogger
    @see dandelion.common.ConsoleLoggers
    @see dandelion.common.ServletLoggers */

public class Loggers
{
    public Logger config;
    public Logger session;
    public Logger instancing;
    public Logger persistence;
    public Logger configuration;
    public Logger db;
    public Logger sql;
    public Logger thread;
    public Logger network;
    public Logger misc;
}
