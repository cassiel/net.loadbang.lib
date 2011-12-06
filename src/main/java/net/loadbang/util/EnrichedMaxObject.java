//	$Id$
//	$Source$

package net.loadbang.util;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.cycling74.max.MaxObject;

/**	A MaxObject sub-class with some utilities (currently, Log4j logging). */

public class EnrichedMaxObject extends MaxObject {
	/**	Logger for the MXJ object instance. */
	private Logger itsLogger = null;

	/**	A set of classes which have announced themselves in this session. */
	private static Set<Class<?>> theAnnounced = new HashSet<Class<?>>();

	/**	Initialise Log4j. */
	static {
		BasicConfigurator.configure();
	}

	/**	Constructor: set up the logging system (once only) if necessary. */
	protected EnrichedMaxObject(String id, String propertyPackagePrefix, Class<?> loggerOwnerClass) {
		itsLogger = Logger.getLogger(loggerOwnerClass);
		
		String p = "        | ";
		
		if (firstInstance(loggerOwnerClass)) {
			post("");
			
			ResourceBundle bundle = ResourceBundle.getBundle(propertyPackagePrefix + ".props.VERSION");
			post(  p + loggerOwnerClass.getName() + " [package v" + bundle.getObject("VERSION")
				 + " / " + bundle.getObject("RELEASE_DATE") + "]"
				);
			post(p + id);
			post(p + "Copyright \u00A9 2009 Nick Rothwell, nick@cassiel.com / nick@loadbang.net");
			post("");
		}
	}
	
	public Logger getLogger() {
		return itsLogger;
	}
	
	private synchronized static boolean firstInstance(Class<?> c) {
		if (theAnnounced.contains(c)) {
			return false;
		} else {
			theAnnounced.add(c);
			return true;
		}
	}
}
