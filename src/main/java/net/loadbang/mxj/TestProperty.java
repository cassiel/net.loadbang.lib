/*	$Id$
	$Source$
 */

package net.loadbang.mxj;

import com.cycling74.max.*;
import java.util.*;

public class TestProperty extends MaxObject {
    public void test(String file) {
	try {
	    ResourceBundle.getBundle(file);
            post("OK: " + file);
	} catch (MissingResourceException _) {
            error("Could not open " + file);
	}
    }
}
