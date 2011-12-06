//	$Id$
//	$Source$

package net.loadbang.oldosc;

import java.util.*;
import java.io.*;

/** A single OSC message.

 	@author  Nick Rothwell (nick@cassiel.com), after Ben Chun (ben@benchun.net)
 */

public class OSCMessage {
    private String itsName;
    private Vector<Character> itsTypes;			// Vector of characters.
    private Vector<Object> itsArguments;

    /** Constructor: set the name, initialise empty types and arguments. */

    public OSCMessage(String name) {
		itsName = name;
		itsTypes = new Vector<Character>();
		itsArguments = new Vector<Object>();
    }

    /** Append a type/argument pair to the current arguments. (Clearly,
     	we expect type and argument to match.) */

    public void addArgument(Character type, Object argument) {
		itsTypes.addElement(type);
		itsArguments.addElement(argument);
    }

    /** Directly sets the type and arg Vectors

		@param   types    a list of types (Characters)
		@param   args     a list of arguments matching the types
	 */

    public void setTypesAndArguments(Vector<Character> types, Vector<Object> args) {
		itsTypes = types;
		itsArguments = args;
    }

    /** Get the name of a message. */

    public String getName() {
        return itsName;
    }

	/**	Get the type tags of a message packed into a string. */

    public String getTypes() {
		String result = "";
		Enumeration t = itsTypes.elements();
	
		while (t.hasMoreElements()) {
		    char ch = ((Character) t.nextElement()).charValue();
		    result += ch;
		}
	
		return result;
    }

	/**	Return an argument (as an object). Assumes that the index is
     	within range. */

    public Object getArgument(int index) {
        return itsArguments.get(index);
    }

    /**	Return a byte array representation of this message.
     	(NICK: I assume that the DataOutputStream encodes things
     	in a sensible network-byte ordering...)
     */

    public byte[] getByteArray() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(baos);
		
		// address (name)
		stream.writeBytes(itsName);
		stream.writeByte(0);
		Tools.alignStream(baos);
	
		// type tags
		stream.writeByte(',');  // comma indicates type tags
		Enumeration t = itsTypes.elements();
	
		while (t.hasMoreElements()) {
		    char type = ((Character) t.nextElement()).charValue();
		    stream.writeByte(type);
		}
		
		stream.writeByte(0);
		Tools.alignStream(baos);
	
		// values
		t = itsTypes.elements();
		Enumeration a = itsArguments.elements();
	
		while (t.hasMoreElements()) {
		    char type = ((Character) t.nextElement()).charValue();
	
		    switch(type) {
			    case 'i':
					stream.writeInt(((Integer) a.nextElement()).intValue());
					break;
		
			    case 'f':
					stream.writeFloat(((Float) a.nextElement()).floatValue());
					break;
		
			    case 'h':
					stream.writeLong(((Long) a.nextElement()).longValue());
					break;
		
			    case 'd':
					stream.writeDouble(((Double) a.nextElement()).doubleValue());
					break;
		
			    case 's':
					stream.writeBytes(((String) a.nextElement()));
					stream.writeByte(0);
					Tools.alignStream(baos);
					break;
		    }    
		}
	
		return baos.toByteArray();
    }
}
