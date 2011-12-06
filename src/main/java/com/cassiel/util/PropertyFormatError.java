/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

public class PropertyFormatError extends Exception {
	private String itsFile, itsKey, itsValue;

    public PropertyFormatError(String file, String key, String value)
    {
	itsFile = file;
        itsKey = key;
        itsValue = value;
    }

    @Override
	public String toString()
    {
	return "PropertyFormat file '" + itsFile + "', key '" + itsKey + "', value '" + itsValue + "'";
    }
}
