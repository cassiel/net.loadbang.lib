//	$Source$
//	$Id$

package net.loadbang.util;

import com.cycling74.max.Atom;

/** An object which can be formatted (printed) as something Max can output. */

public interface MaxFormattable {
    /** Format the object as an array of Atoms. */
    public Atom[] maxFormat();
}
