//	$Source$
//	$Id$

package net.loadbang.mxj.oldosc.demo.reflect;

import net.loadbang.mxj.oldosc.OscHandler;

/** A test handler (the root part of it).

@author nick
*/

public class LeafHandler implements OscHandler {
    private int itsValue;
    
    public void setValue(int value) {
        itsValue = value;
    }
}
