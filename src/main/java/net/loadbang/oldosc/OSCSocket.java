//	$Id$
//	$Source$

package net.loadbang.oldosc;

import java.net.*;
import java.io.IOException;

/** Socket for sending OSC packets. The packets actually carry the host address
 	and port, although in the Max world that's a property of the socket writing object. */

public class OSCSocket extends DatagramSocket {
    /** Constructor. */

    public OSCSocket() throws SocketException {
        super();
    }

    /**
     * The only override, to send an OscPacket.
     *
     * @param oscPacket The OSC packet
     */

    public void send(OSCPacket oscPacket) throws IOException {
        byte[] byteArray = oscPacket.getByteArray();

		// DEBUG
		//	System.out.println("OscSocket about to send this packet:");
		//	OscPacket.printBytes(byteArray);

        DatagramPacket packet =
		    new DatagramPacket(byteArray, byteArray.length,
							   oscPacket.getAddress(), oscPacket.getPort()
							  );

        send(packet);
    }
}
