//	$Source$
//	$Id$

package net.loadbang.spring;

import java.io.File;
import java.util.HashMap;

import net.loadbang.util.FileUtils;
import net.loadbang.util.exn.ResourceException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**	A helper class which can build a Spring context. We assume that the same filename root
	refers to the same (shared) context, so we cache them. We also reference-count the
	contexts, keyed by the filename.
	
	@author Nick Rothwell, nick@loadbang.net / nick@cassiel.com
 */

public class MaxSpringHelper {
	private static Logger itsLogger = Logger.getLogger(MaxSpringHelper.class);

	private static HashMap<String, ContextHashEntry> theContexts =
		new HashMap<String, ContextHashEntry>();

	static private synchronized ApplicationContext findContext1(String placeHolder)
		throws ResourceException
	{
		ContextHashEntry e00 = theContexts.get(placeHolder);
		
		if (e00 == null) {
			String filename = placeHolder + ".xml";
			File f00 = FileUtils.locateFile00(filename);
			
			if (f00 == null) {
				throw new ResourceException("cannot find " + filename);
			} else {
				itsLogger.debug("creating context on " + placeHolder);

				AbstractApplicationContext ctx =		//	AbstractApplicationContext provides close().
					new FileSystemXmlApplicationContext("file:" + f00.getPath());

				theContexts.put(placeHolder, new ContextHashEntry(ctx));
				return ctx;
			}
		} else {
			itsLogger.debug("already found context: " + placeHolder);
			e00.bumpRefCount();
			return e00.getContext();
		}

	}

	private static synchronized void closeContext1(String placeHolder)
		throws ResourceException
	{
		ContextHashEntry e00 = theContexts.get(placeHolder);
		
		if (e00 == null) {
			throw new ResourceException("internal: have lost " + placeHolder);
		} else {
			e00.dropRefCount();

			if (e00.noRefs()) {
				e00.getContext().close();
				theContexts.remove(placeHolder);
			}
		}
	}
	
	public ApplicationContext findContext(String placeHolder)
		throws ResourceException
	{
		return findContext1(placeHolder);
	}
	
	public void closeContext(String placeHolder) throws ResourceException {
		closeContext1(placeHolder);
	}
}
