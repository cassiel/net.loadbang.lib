//	$Source$
//	$Id$

package net.loadbang.oldosc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**	Some generic OSC utilities. */

public class Tools {
    /**	Align an output stream to a 4-byte boundary. */

    public static void alignStream(ByteArrayOutputStream stream)
    	throws IOException
    {
        int pad = 4 - (stream.size() % 4);
        
        if (pad == 4) { pad = 0; }
 
        for (int i = 0; i < pad; i++) {
            stream.write(0);
        }
    }
}
