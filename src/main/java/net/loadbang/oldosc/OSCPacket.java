package net.loadbang.oldosc;

import java.util.*;
import java.io.*;
import java.net.InetAddress;

import com.cassiel.util.GlobalHooks;

/** An OSC packet of one or more OSC messages.

 	@author Nick Rothwell (nick@cassiel.com), after Ben Chun (ben@benchun.net)
 */

public class OSCPacket {
    private long itsTime;					//	TODO: timestamps not used at present.
    private Vector<OSCMessage> itsMessages;
    private InetAddress itsAddress;
    private int itsPort;

    /**	Constructor for all incoming packets and those outgoing packets
     	whose time, address, and port will be set later. */

    public OSCPacket(InetAddress address, int port) {
    		this(0, address, port);
    }

    /** Constructor for outgoing packets.
     	@param  time    OSC time tag
     	@param  address destination host
     	@param  port    destination port
     */

    public OSCPacket(long time, InetAddress address, int port) {
		itsTime = time;
		itsMessages = new Vector<OSCMessage>();
		itsAddress = address;
		itsPort = port;
	
		GlobalHooks.loggers().network.log(">>> forming packet for "
						  				  + address + ":" + port
						  				  + ", time " + time
						 				 );
    }

    /**	Set the time. */

    public void setTime(long time) {
        itsTime = time;
    }

    /** Set the destination address. */

    public void setAddress(InetAddress address) {
        itsAddress = address;
    }

    /** Set the destination port. */

    public void setPort(int port) {
        itsPort = port;
    }

    /** Add a message to this packet. */
    
    public void addMessage(OSCMessage message) {
		GlobalHooks.loggers().network.log(
		    ">>> adding message '" + message.getName() + "'"
		);

		itsMessages.addElement(message);
    }

    public InetAddress getAddress() {
        return itsAddress;
    }

    public int getPort() {
        return itsPort;
    }

    /**	Return a byte array representation of this packet, suitable for
    		sending to OSC client applications. */

    public byte[] getByteArray() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(baos);
	
		// bundle
      	if (itsMessages.size() > 1) {
		    baos.write(("#bundle").getBytes());
		    baos.write(0);
		    // bundles have a time tag
		    stream.writeLong(itsTime);
		}
	
		// messages
		Enumeration m = itsMessages.elements();

		while (m.hasMoreElements()) {
		    OSCMessage mess = (OSCMessage) m.nextElement();
		    byte[] byteArray = mess.getByteArray();

		    // bundles have message size tags
		    if (itsMessages.size() > 1) {
		        stream.writeInt(byteArray.length);
		    }

		    baos.write(byteArray);
		}

		Tools.alignStream(baos);
		return baos.toByteArray();
    }

    public Enumeration getMessages() {
        return itsMessages.elements();
    }

    /**	Dump the entire packet to standard output: */
    
    public void dump() {
        String hex = "", ascii = "";
        
        try {
            byte[] data = getByteArray();
            int len = data.length;
            
            System.out.println("len = " + len);

            for (int i = 0; i < len; i++) {
                byte d = data[i];
                hex += " ";
                if (d < 8) { hex += "0"; }
                hex += Integer.toHexString(d);

                if (d < 32 || d > 127) {
                    ascii += ".";
                } else {
                    ascii += new String(new byte[] { d });
                }

                if (i % 8 == 7) {
                    System.out.println("|" + ascii + "| " + hex);
                    hex = "";
                    ascii = "";
                }
            }
            
            if (len % 8 != 0) {
                for (int i = ascii.length(); i < 8; i++) { ascii += " "; }
                System.out.println("|" + ascii + "| " + hex);
            }
        } catch (IOException exn) {
            System.err.println("IO exception on dump(): " + exn);
        }
    }
}
