//	$Source$
//	$Id$

package net.loadbang.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cycling74.max.MaxSystem;

/** Some library utilities over files. */

public class FileUtils {
	private static Pattern itsStripExtensionPattern = Pattern.compile("(.*)\\.[^.]*");
	private static Pattern itsGetExtensionPattern = Pattern.compile(".*\\.([^.]*)");
	
	/** Return the File object for a file in the Max search path.

		@param file The unqualified file name.
		@return a File object for the file, or null if file not found in the
		search path.
	 */

    static public File locateFile00(String file) {
        String result00 = MaxSystem.locateFile(file);
        
        if (result00 != null) {
            return new File(result00);
        } else {
            return null;
        }
    }
    
	/**	Return a File object for a file on the Max search path, or fail assertion
	 	if it's not found. */
    
	static public File locateFile(String file) {
		File f00 = locateFile00(file);

		assert f00 != null : "FileUtils.locateFile(" + file + ")";
		return f00;
	}
    
	/**	Strip any extension from a filename.
	 
	 	@param file the file name
	 	@return the file name with any extension stripped.
	 */
	
	static public String stripExtension(String file) {
		Matcher m = itsStripExtensionPattern.matcher(file);
		
		if (m.matches()) {
			return m.group(1);
		} else {
			return file;
		}
	}
	
	/**	Determine the extension of a filename (or even something more
	 	complicated, like a URL). Does not include the leading ".".
	 */
	
	static public String getExtension00(String file) {
		Matcher m = itsGetExtensionPattern.matcher(file);
		
		if (m.matches()) {
			return m.group(1);
		} else {
			return null;
		}
	}
}
