//	$Id$
//	$Source$

package net.loadbang.mxj.oldosc;

import net.loadbang.oldosc.OSCMessage;
import net.loadbang.oldosc.OSCPacket;
import net.loadbang.oldosc.OSCSocket;

import com.cycling74.max.MaxObject;
import com.cycling74.max.Atom;
import java.net.InetAddress;
import java.io.IOException;

/** OSC sender object for MXJ. This sends data generically from a message plus Atom array. */

public class OscUdpWrite extends MaxObject {
    /**	The socket for sending OSC packets. */
    private OSCSocket itsSocket;
    
    /** Intermediate OSC bundle, flushed out on bang. */
    private OSCPacket itsPendingPacket00 = null;
    
    /**	Flag to specify whether to bundle messages. Default is OFF. */
    private boolean itsBundleMessages = false;

    //	Host and port are set up as attributes in the MXJ object, even
    //	though the vales are part of the packets.

    /** Internet address of the destination. */
    private InetAddress itsInetAddress00 = null;
    
    /**	Destination port. */
    private Integer itsPort00 = null;
    
    public OscUdpWrite() {
        try {
	        declareAttribute("host", "getHost", "setHost");
	        declareAttribute("port", "getPort", "setPort");
	        declareAttribute("bundle", "getBundle", "setBundle");
	        itsSocket = new OSCSocket();
        } catch (java.net.SocketException exn) {
            bail("socket exception: " + exn);
        }
        
        declareIO(1, 0);		//	One inlet, plus info outlet only.
	    setInletAssist(0, "message to send, 'host', 'port' or bang");
    }
    
    /** The packets contain the IP and port for transmission, so if we have
      	a pending packet we can change its settings.
     */
    
    private synchronized void updatePending() {
        if (itsPendingPacket00 != null) {
            itsPendingPacket00.setAddress(itsInetAddress00);
            itsPendingPacket00.setPort(itsPort00.intValue());
        }
    }
    
    /** Change the destination host.
     * 
     *  @param host The name or IP address of the host.
     */

    public void setHost(String host) {
        try {
            itsInetAddress00 = InetAddress.getByName(host);
            post("OscUdpWrite: sending to " + itsInetAddress00);
            updatePending();
        } catch (java.net.UnknownHostException _) {
            error("no such host: " + host);
        }
    }
    
    /**	Return the current host. */
    
    public String getHost() {
        if (itsInetAddress00 == null) {
            error("host not set");
            return "none";
        } else {
            return itsInetAddress00.toString();
            	//	I want to implement a getCanonicalHostName() as well, but
            	//	that should probably be deferred as it will access DNS.
        }
    }
    
    public int getPort() {
        if (itsPort00 == null) {
            error("port not set");
            return 0;
        } else {
            return itsPort00.intValue();
        }
    }

    /** Change the destination port.
     * 
     *  @param port The port to send to.
     */

    public void setPort(int port) {
        itsPort00 = new Integer(port);
        updatePending();
    }
    
    /**	Set the bundle status. */
    public void setBundle(boolean bundle) {
        itsBundleMessages = bundle;
    }
    
    /**	Get the bundle status. */
    public boolean getBundle() {
        return itsBundleMessages;
    }
    
    /**	Write an arbitrary message to the socket. */
    
    @Override
	protected void notifyDeleted() {
        itsSocket.close();
    }
    
    /**	Generic messages are packed and sent to the socket (if the
     	host and port have been set up).
     */
    
    @Override
	synchronized protected void anything(String message, Atom args[]) {
        if (itsInetAddress00 == null) {
            error("host not set");
        } else if (itsPort00 == null) {
            error("port not set");
        } else  {
	        if (itsPendingPacket00 == null) {
	            itsPendingPacket00 = new OSCPacket(itsInetAddress00, itsPort00.intValue());
	        }
	        
	        OSCMessage m = new OSCMessage(message);
	        
	        for (int i = 0; i < args.length; i++) {
	            Atom a = args[i];
	            
	            if (a.isFloat()) {
	                m.addArgument(new Character('f'), new Float(a.getFloat()));
	            } else if (a.isInt()) {
	                m.addArgument(new Character('i'), new Integer(a.getInt()));
	            } else if (a.isString()) {
	                m.addArgument(new Character('s'), a.getString());
	            } else {
	                error("cannot encode argument: " + a);
	            }
	        }

	        itsPendingPacket00.addMessage(m);
	        
	        if (!itsBundleMessages) {
	            flush();
	        }
        }
    }
    
    /**	Output the pending packet. (Not thread-safe.) */
    
    private void flush() {
        if (itsPendingPacket00 != null) {
            try {
                //itsPendingPacket00.dump();
                itsSocket.send(itsPendingPacket00);
            } catch (IOException exn) {
                error("sending OSC packet: " + exn);
            }

            itsPendingPacket00 = null;
        }
    }

    /**	Output the pending packet. */
    @Override
	synchronized protected void bang() {
        flush();
    }
}
