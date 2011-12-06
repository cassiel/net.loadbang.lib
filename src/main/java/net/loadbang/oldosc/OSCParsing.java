package net.loadbang.oldosc;

import java.util.*;

import com.cassiel.util.GlobalHooks;
import com.cassiel.util.Logger;

/** OSC packet parsing utilities. (These were in OscServer but didn't
    actually require its state.) */

public class OSCParsing {
    /**
     * Takes a byte array starting with a string padded with null
     * characters so that the length of the entire block is a multiple
     * of 4, and seperates it into a String and a byte array of the
     * remaining data.  These are then returned in a Vector.
     *
     * @param   block           block of data beginning with a string
     * @param   stringLength    number of characters in the string
    */	
    static public Vector getStringAndData(byte[] block, int stringLength) {
	Vector v = new Vector();
	int i;
	
	if ( stringLength %4 != 0) {
	    GlobalHooks.loggers().misc.log(Logger.ERROR, "printNameAndArgs: bad boundary");
	    return v;
	}

	for (i = 0; block[i] != '\0'; i++) {
	    if (i >= stringLength) {
		GlobalHooks.loggers().misc.log(Logger.ERROR, "printNameAndArgs: Unreasonably long string");
		return v;
	    }
	}
	// v.firstElement() is the String
	v.addElement( new String(Bytes.copy(block, 0, i)) );

	i++;
	for (; (i % 4) != 0; i++) {
	    if (i >= stringLength) {
		GlobalHooks.loggers().misc.log(Logger.ERROR, "printNameAndArgs: Unreasonably long string");
		return v;
	    }
	    if (block[i] != '\0') {
		GlobalHooks.loggers().misc.log(Logger.ERROR, "printNameAndArgs: Incorrectly padded string.");
		return v;
	    }
	}
	// v.elementAt(1) is the position in the original byte[] where the data starts
	v.addElement( new Integer(i) );
	// v.lastElement() is the byte[] of data
	v.addElement( Bytes.copy( block, i ) );
	return v;
    }

    /**
     * Returns an array of Vectors containing types and arguments.
     *
     * @param   block   byte array containing types and arguments
    */
    static public Vector[] getTypesAndArgs( byte[] block ) {
	// TBD : throw exceptions or something when there are no type tags
	int n = block.length;
	Vector[] va = new Vector[2];
	if (n != 0) {
	    if (block[0] == ',') {
		if (block[1] != ',') {
		    /* This message begins with a type-tag string */
		    va = getTypeTaggedArgs( block );
		} else {
		    /* Double comma means an escaped real comma, not a
		     * type string */
		    va = getHeuristicallyTypeGuessedArgs( block );
		}
	    } else {
		va = getHeuristicallyTypeGuessedArgs( block );
	    }
	}
	return va;
    }

    /**
     * Returns Vectors containing the types and arguments from a
     * type-tagged byte array
     *
     * @param   block   a byte array with type-tagged data
    */
    static public Vector[] getTypeTaggedArgs( byte[] block ) {
	Vector<Character> typeVector = new Vector<Character>();
	Vector<Object> argVector = new Vector<Object>();
	int p = 0;

	/* seperate the block into the types byte array and the
	 * argument byte array*/
	Vector typesAndArgs = getStringAndData(block, block.length);
	byte[] args = (byte[]) typesAndArgs.lastElement();
	
	for (int thisType=1; block[thisType] != 0; thisType++) {
	    switch (block[thisType]) {

	    case '[' :
		typeVector.addElement(new Character('['));
		break;

	    case ']' :
		typeVector.addElement(new Character(']'));
		break;

	    case 'i': case 'r': case 'm': case 'c':
		typeVector.addElement(new Character('i'));
		argVector.addElement( new Integer( Bytes.toInt( Bytes.copy(args, p, p+4) )) );
		p += 4;
		break;
		
	    case 'f':
		typeVector.addElement(new Character('f'));
		argVector.addElement( new Float( Bytes.toFloat( Bytes.copy(args, p, p+4) )) );
		p += 4;
		break;
		
	    case 'h': case 't':
		typeVector.addElement(new Character('h'));
		argVector.addElement( new Long( Bytes.toLong( Bytes.copy(args, p, p+8) )) );
		p += 8;
		break;
		
	    case 'd':
		typeVector.addElement(new Character('d'));
		argVector.addElement( new Double( Bytes.toDouble( Bytes.copy(args, p, p+8) )) );
		p += 8;
		break;
		
	    case 's': case 'S':
		typeVector.addElement(new Character('s'));
		byte[] remaining = Bytes.copy(args, p);
		Vector v = getStringAndData( remaining, remaining.length );
		argVector.addElement( (String)v.firstElement() );
		p += ((Integer)v.elementAt(1)).intValue();
		break;
		
	    case 'T':
		typeVector.addElement(new Character('T')); 
		break;
	    case 'F':
		typeVector.addElement(new Character('F'));
		break;
	    case 'N':
		typeVector.addElement(new Character('N'));
		break;
	    case 'I':
		typeVector.addElement(new Character('I'));
		break;

	    default:
		GlobalHooks.loggers().misc.log(Logger.ERROR, "[Unrecognized type tag " +
			       block[thisType] + "]" );
	    }
	}

	Vector[] returnValue = new Vector[2];
	returnValue[0] = typeVector;
	returnValue[1] = argVector;
	return returnValue;
    }
	

    /**
     * Returns the arguments from a non-type-tagged byte array
     *
     * @param   block   a byte array containing data
    */
    static public Vector[] getHeuristicallyTypeGuessedArgs( byte[] block ) {
	// TBD : handle packets without type tags
	GlobalHooks.loggers().misc.log(Logger.ERROR, "Bad OSC packet: No type tags");
	return new Vector[2];
    }

    /**
     * Prints out a byte array in 4-byte lines, useful for debugging.
     *
     * @param byteArray The byte array
     */
    public static void printBytes(byte[] byteArray) {
	for (int i=0; i<byteArray.length; i++) {
	    System.out.print(byteArray[i] + " (" + (char)byteArray[i] + ")  ");
	    if ((i+1)%4 == 0)
		System.out.print("\n");
	}
    }
}
