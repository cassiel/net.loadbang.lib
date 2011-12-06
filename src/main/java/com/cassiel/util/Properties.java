/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

import java.util.*;
import java.util.regex.*;

public class Properties {
    public static String getString(String file, String key)
        throws PropertyNotFoundError
    {
	try {
	    ResourceBundle bundle = ResourceBundle.getBundle(file);
            String s = bundle.getString(key);
            //System.out.println("Properties: " + key + " -> '" + s + "'");
	    return s;
	} catch (MissingResourceException _) {
	    throw new PropertyNotFoundError(file, key);
	}
    }
    
    public static String getString(String file,
				   String key,
				   String defaultValue
				  )
    {
        try {
            return getString(file, key);
        } catch (PropertyNotFoundError _) {
            return defaultValue;
        }
    }
    
    public static int getInteger(String file, String key)
        throws PropertyNotFoundError, PropertyFormatError
    {
        String s = getString(file, key);
        
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException _) {
	    System.err.println("Properties: non-integer value in '" + s + "'");
            throw new PropertyFormatError(file, key, s);
        }
    }

    public static int getInteger(String file, String key, int defaultValue)
        throws PropertyFormatError
    {
        try {
            return getInteger(file, key);
        } catch (PropertyNotFoundError _) {
            return defaultValue;
        }
    }
    
    public static String[] getStrings(String file, String key)
        throws PropertyNotFoundError
    {
        String data = getString(file, key);
        Pattern p = Pattern.compile("\\s+");
        return p.split(data);
    }
}
