//	$Id$
//	$Source$

package net.loadbang.util.mxj;

import net.loadbang.spring.MaxSpringHelper;
import net.loadbang.util.EnrichedMaxObject;
import net.loadbang.util.exn.ResourceException;

import org.springframework.context.ApplicationContext;

/**	A quick test for loading a Spring config file from Max's search path.
	
	@author Nick Rothwell, <TT>nick@cassiel.com</TT> / <TT>nick@loadbang.net</TT>
*/


public class MaxSpringTest extends EnrichedMaxObject {
	/**	Spring configuration name. */
	private String itsSpringConfig;
	
	/** Constructor. Initialises Log4j, sets up attributes. */
	public MaxSpringTest() {
		super("$Id$",
			  "net.loadbang.util",
			  MaxSpringTest.class
			 );
		
        declareAttribute("springconfig", "getSpringConfig", "setSpringConfig");
	}
	
	public String getSpringConfig() {
		return itsSpringConfig;
	}

	public void setSpringConfig(String config) {
		try {
			ApplicationContext context = new MaxSpringHelper().findContext(config);
		} catch (ResourceException exn) {
			getLogger().error("error opening config " + config, exn);
		}
	}
}
