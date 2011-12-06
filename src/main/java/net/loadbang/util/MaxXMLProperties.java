//	$Id$
//	$Source$

package net.loadbang.util;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import net.loadbang.util.exn.MaxXMLPropertiesException;

/**	Wrapper for properties loaded from an XML in MaxMSP's search path. */

public class MaxXMLProperties {
	private java.util.Properties itsProperties;

	public MaxXMLProperties(String filename)
		throws MaxXMLPropertiesException
	{
		itsProperties = new java.util.Properties();
		File file00 = FileUtils.locateFile00(filename);
		
		if (file00 != null) {
			InputStream instream00 = null;

			try {
				instream00 = new FileInputStream(file00);
				itsProperties.loadFromXML(instream00);
			} catch (Exception exn) {
				throw new MaxXMLPropertiesException("loadbang properties", exn);
			} finally {
				if (instream00 != null) {
					try { instream00.close(); } catch (Exception _) { }
				}
			}
		} else {
			throw new MaxXMLPropertiesException("place-holder not found: " + filename);
		}
	}
	
	public String getString(String key)
		throws MaxXMLPropertiesException
	{
		String value00 = itsProperties.getProperty(key);
		
		if (value00 == null) {
			throw new MaxXMLPropertiesException("key not found: " + key);
		} else {
			return value00;
		}
	}
	
	public int getInteger(String key)
		throws MaxXMLPropertiesException
	{
		try {
			return Integer.parseInt(getString(key));
		} catch (NumberFormatException exn) {
			throw new MaxXMLPropertiesException("format: " + key, exn);
		}
	}
	
	public boolean getBoolean(String key)
		throws MaxXMLPropertiesException
	{
		return Boolean.parseBoolean(getString(key));
	}
}
