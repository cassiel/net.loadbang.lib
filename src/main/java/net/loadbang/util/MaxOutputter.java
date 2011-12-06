//	$Source$
//	$Id$

package net.loadbang.util;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

/** Callback class: something which can output Max-printable objects, and which
 	can be wrapped around a MaxObject which does the outputting.
 	
 	We (now) have an optional token which will get prefixed to everything that's
 	output; this is because our Java objects will generally have various output
 	routines, and will wish to separate them with a route(). */

public class MaxOutputter {
	/**	Optional prefix string. */
	private String itsPrefix00;

    /**	The MaxObject which will do the outputting (to its leftmost outlet). */
    private MaxObject itsMaxObject;

    /**	Constructor: wrap around a MaxObject. */
    public MaxOutputter(MaxObject maxObject, String prefix00) {
    	itsPrefix00 = prefix00;
        itsMaxObject = maxObject;
    }
    
    public MaxOutputter(MaxObject maxObject) {
    	this(maxObject, null);
    }
    
    /**	Append two arrays of atoms. */
    private Atom[] append(Atom[] fst, Atom[] snd) {
        Atom[] result = new Atom[fst.length + snd.length];
        
        System.arraycopy(fst, 0, result, 0, fst.length);
        System.arraycopy(snd, 0, result, fst.length, snd.length);
        
        return result;
    }

    /** Put out an array of Atoms, with an optional initial message. */
    public MaxOutputter output(int outlet, String tokens00, Atom[] args00) {
        Atom[] tokens;
        
        if (itsPrefix00 != null) {
        	tokens = new Atom[] { Atom.newAtom(itsPrefix00) };
        } else {
        	tokens = Atom.emptyArray;
        }
        
        if (tokens00 != null) {
            tokens = append(tokens, Atom.parse(tokens00));
        }
        
        if (args00 == null) {				//	Uh? Can one pass in null as an Atom[]?
            args00 = Atom.emptyArray;
        }
        
        if (tokens.length + args00.length > 0) {
	        itsMaxObject.outlet(outlet, append(tokens, args00));
        }
        
        return this;
    }
    
    /**	Output a string (nullable) and two arrays: */
    public MaxOutputter output(int outlet, String tokens00, Atom[] fst, Atom[] snd) {
    	return output(outlet, tokens00, append(fst, snd));
    }
    
    /**	Output some atoms. */
    public MaxOutputter output(int outlet, Atom[] a) {
    	return output(outlet, (String) null, a);
    }
    
    /**	Output two atom arrays. */
    public MaxOutputter output(int outlet, Atom[] a, Atom[] b) {
    	return output(outlet, null, a, b);
    }
    
    /**	Put out an initial message. */
    public MaxOutputter output(int outlet, String tokens00) {
        return output(outlet, tokens00, Atom.emptyArray);
    }
    
    /** Optionally output some tokens, and a Max-formattable object,
     	in a single message. */

    public MaxOutputter output(int outlet, String tokens00, MaxFormattable object) {
        return output(outlet, tokens00, object.maxFormat());
    }
}
