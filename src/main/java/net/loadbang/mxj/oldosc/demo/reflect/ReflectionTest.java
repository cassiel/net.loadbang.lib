//	$Source$
//	$Id$

package net.loadbang.mxj.oldosc.demo.reflect;

import net.loadbang.mxj.oldosc.OscHandler;
import net.loadbang.mxj.oldosc.OscHandlerLoader;

/** Test console program for OSC reflection.

 	@author nick
 */

public class ReflectionTest {
    public static void main(String[] args) {
        OscHandler root = new RootHandler();
        
        new OscHandlerLoader(root).dump();
    }
}
