package net.loadbang.oldosc;

import java.util.*;
import java.io.*;
import java.net.*;

import com.cassiel.util.GlobalHooks;
import com.cassiel.util.Logger;

/** OscServer
    <BR><BR>
    OpenSoundControl UDP Server for Gateway.

    Based on CommServer by Derek Clayton and dumpOSC by Matt Wright.

    Thanks to Jesse Gilbert (j@resistantstrain.net) for helping me with
    the static socket.

    Seriously overhauled by Nick Rothwell, nick@cassiel.com.
   
    @author  Ben Chun        ben@benchun.net
    @version 1.0
 */

public abstract class OSCServer extends Thread {
    private DatagramSocket itsOscSocket00 = null;	// incoming UDP socket
    private int itsPort;							// incoming UDP port

    /** Constructor for the OscServer.
     	@param port UDP port for OSC communication.
     */

    public OSCServer(int port) {
        itsPort = port;
    }

    /** Thread run method.  Monitors incoming messages. */
    
    @Override
	public void run() {
		try {
		    InetAddress clientAddress;
		    int clientPort;
	
		    // --- create a new UDP OSC socket
		    itsOscSocket00 = new DatagramSocket(itsPort);
	
		    GlobalHooks.loggers().misc.log(
		        "UDP OSC server started on port: " + itsPort
		    );
	
		    while (true) {
				byte[] datagram  = new byte[Manifest.OSC_MAX_LEN];
		
				DatagramPacket dp = new DatagramPacket(datagram, datagram.length);
		
				// block until a datagram is received
				itsOscSocket00.receive(dp);
		
				clientAddress = dp.getAddress();
				clientPort = dp.getPort();
		
				// parse the packet
				GlobalHooks.loggers().misc.log(
				     "Received UDP packet from "
				     + clientAddress.toString()
				     + ":" + clientPort
				     + ", len=" + dp.getLength()
			        );
		
				OSCPacket packet = new OSCPacket(clientAddress, clientPort);
		
				parseOscPacket(datagram, dp.getLength(), packet);
		
				// DEBUG
				//System.out.println("** raw packet **");
				//OscPacket.printBytes( datagram );
		
				if (GlobalHooks.loggers().network.isDebugging()) {
				    //System.out.println("** constructed packet **");
				    //OscParsing.printBytes( packet.getByteArray() );
				}
		
				//System.out.println("Packet dump:");
				//packet.dump();
		
				processMessages(
				    new InetSocketAddress(clientAddress, clientPort), packet
				);
		    }
	    } catch (IOException ioe) {
	        GlobalHooks.loggers().misc.log(
				Logger.ERROR,
				"Server error ... Stopping UDP OSC server ... "
				+ ioe.toString()
			);

            // kill this server
            killServer();
        }
    }

    /** Process a single OSC message from a client.
        @param clientAddress The socket address of the client.
        @param message The OSC message.
     */

    public abstract void processMessage(InetSocketAddress clientAddress,
            							OSCMessage message
									   );

    /** Process all the messages in an OSC packet from a client.
        @param clientAddress The socket address of the client.
        @param packet The OSC packet.
     */

    private void processMessages(InetSocketAddress clientAddress,
				 				 OSCPacket packet
								) {
		Enumeration e = packet.getMessages();
	
		while (e.hasMoreElements()) {
		    OSCMessage m = (OSCMessage) e.nextElement();
	
		    GlobalHooks.loggers().network.log(
		        ">>> incoming '" + m.getName() + "'"
		    );
	
		    processMessage(clientAddress, m);
		}
    }

    /**
     * This method is based on dumpOSC::ParseOSCPacket.  It verifies
     * that the packet is well-formed and puts the data into an
     * OscPacket object, or else prints useful error messages about
     * what went wrong.
     *
     * @param   datagram  a byte array containing the OSC packet
     * @param   n         size of the byte array
     * @param   packet    the OscPacket object to put data into
     */
    private void parseOscPacket(byte[] datagram, int n, OSCPacket packet) {
		int size, i;
	
		if ((n % 4) != 0) {
		    GlobalHooks.loggers().misc.log(
				Logger.ERROR,
				"SynthControl packet size (" + n +
				") not a multiple of 4 bytes, dropped it."
		    );
	
		    return;
		}
	
		String dataString = new String(datagram);

		if ((n >= 8) && dataString.startsWith("#bundle")) {
		    /* This is a bundle message. */
		    
		    if (n < 16) {
				GlobalHooks.loggers().misc.log(
				    Logger.ERROR,
				    "Bundle message too small (" + n +
				    " bytes) for time tag, dropped it."
				);
		
				return;
		    }
	
		    /* Get the time tag */
		    Long time = new Long(Bytes.toLong(Bytes.copy(datagram, 8, 8)));
		    packet.setTime(time.longValue());
	
		    i = 16; /* Skip "#bundle\0" and time tags */

		    while(i < n) {
				size = (Bytes.toInt(Bytes.copy(datagram, i, i + 4)));

				if ((size % 4) != 0) {
				    GlobalHooks.loggers().misc.log(
						Logger.ERROR,
						"Bad size count" + size +
						"in bundle (not a multiple of 4)"
				    );
		
				    return;
				}

				if ((size + i + 4) > n) {
				    GlobalHooks.loggers().misc.log(
						Logger.ERROR,
						"Bad size count" + size + "in bundle" +
						"(only" + (n-i-4) + "bytes left in entire bundle)"
				    );

				    return;
				}
				
				/* Recursively handle element of bundle */
				byte[] remaining =  Bytes.copy(datagram, i + 4);
				parseOscPacket(remaining, size, packet);
				i += (4 + size);
		    }
		} else {
		    /* This is not a bundle message */	    
		    Vector nameAndData = OSCParsing.getStringAndData(datagram, n);
	
		    String name = (String) nameAndData.firstElement();
		    OSCMessage message = new OSCMessage(name);
	
		    byte[] data = (byte[]) nameAndData.lastElement();
		    Vector[] typesAndArgs = OSCParsing.getTypesAndArgs(data);
		    message.setTypesAndArguments(typesAndArgs[0], typesAndArgs[1]);
	
		    packet.addMessage(message);
		}
    }

    /** Stop the UDP server. */
    public void killServer() {
		if (itsOscSocket00 != null) {
		    itsOscSocket00.close();
		}
	
		GlobalHooks.loggers().misc.log("UDP OSC server stopped");
    }
}
