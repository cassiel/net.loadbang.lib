//	$Source$
//	$Id$

package net.loadbang.mxj.oldosc.demo;

import java.net.InetSocketAddress;

import net.loadbang.oldosc.OSCMessage;
import net.loadbang.oldosc.OSCServer;

import com.cycling74.max.*;

/** The actual receiver of OSC messages. */

public class ReceiverOutletter extends OSCServer {
    private MaxObject itsMaxObject;
    
    /**	Constructor. Remembers the calling MaxObject so that it can do output. */
    public ReceiverOutletter(int port, MaxObject maxObject) {
        super(port);
        itsMaxObject = maxObject;
    }

    @Override
	public void processMessage(InetSocketAddress clientAddress,
            				   OSCMessage message
		       				  ) {
        String types = message.getTypes();
        Atom[] a = new Atom[types.length() + 1];
        
        a[0] = Atom.newAtom(message.getName());
        
        for (int i = 0; i < types.length(); i++) {		//	TODO: action if empty?
            Object obj = message.getArgument(i);

            switch (types.charAt(i)) {
            	case 'i':
            	    a[i + 1] = Atom.newAtom(((Integer) obj).intValue());
            		break;
            	    
            	case 'f':
            	    a[i + 1] = Atom.newAtom(((Float) obj).floatValue());
            	    break;
  
            	case 'h':
            	    a[i + 1] = Atom.newAtom(((Long) obj).longValue());
            	    break;		//	TODO: deal with overflow.

            	case 'd':
            	    a[i + 1] = Atom.newAtom(((Double) obj).doubleValue());
            	    break;		//	TODO: deal with overflow.
            	    
            	case 's':
            	    a[i + 1] = Atom.newAtom((String) obj);
            	    
            	default: 	//	TODO: deal with error.
            }
        }
    
        itsMaxObject.outletHigh(0, a);
        //	TODO: put out the IP as well.
    }
}
