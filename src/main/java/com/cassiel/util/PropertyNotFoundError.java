/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

public class PropertyNotFoundError extends Exception {
	private String itsFile, itsKey;

    public PropertyNotFoundError(String file, String key)
    {
	itsFile = file;
        itsKey = key;
    }

    @Override
	public String toString()
    {
	return "PropertyNotFound, file '" + itsFile + "', key '" + itsKey + "'";
    }
}
