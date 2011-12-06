//	$Source$
//	$Id$

package net.loadbang.mxj.oldosc.demo.reflect;

import net.loadbang.mxj.oldosc.OscHandler;

/** A test handler (the root part of it).

	@author nick
 */

public class RootHandler implements OscHandler {
    private LeafHandler itsA, itsB;
    
    public RootHandler() {
        itsA = new LeafHandler();
        itsB = new LeafHandler();
    }
    
    public OscHandler getA() { return itsA; }
    public OscHandler getB() { return itsB; }
    public void setF(float f) { }
    public void setI(int i) { }
    public void setA(float[] a) { }
    public Object getSomeOther() { return new Integer(0); }
}
