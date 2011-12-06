//	$Source$
//	$Id$

package net.loadbang.mxj.oldosc.demo;

import com.cycling74.max.MaxObject;

/** Demo MXJ object, which uses the demo Receiver object (which just puts out
 	received data to the outlet at high priority). */

public class ReceiveOSC extends MaxObject {
    public ReceiveOSC() {
        declareTypedIO("M", "M");
        createInfoOutlet(false);
        
        declareAttribute("listen", null, "listen");
    }
    
    /*private (reflection)*/ void listen(int port) {
        //	TODO: are we allowed to launch this if "listen" is set up in an attribute? Clarify
        //	whether attributes are called at ctor time. Certainly, any incoming data should not
        //	cause output. (So maybe we need an explicit "start" method.)
        //	TODO: defer the "listen" action:
        new ReceiverOutletter(port, this).start();
    }
}
