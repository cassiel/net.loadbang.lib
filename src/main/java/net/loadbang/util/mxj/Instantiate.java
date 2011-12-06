/* $Id$ */

package net.loadbang.util.mxj;

import com.cycling74.max.*;

/** A little scripting helper for reloading MXJ objects: delete and re-instantiate
 *  an arbitrary object, optionally connecting a single upstream and a single
 *  downstream object.
 */

public class Instantiate extends MaxObject {
    private int itsX, itsY;
    private String itsUpstreamName, itsScriptingName, itsDownstreamName, itsObjectName;
    private Atom[] itsArgs;
    
    private static final String VOID_NAME = "-";

    Instantiate(Atom[] args) {
		if (   args.length >= 6
		    && args[0].isString()		// Upstream scripting name
		    && args[1].isString()		// Scripting name
		    && args[2].isString()		// Downstream scripting name
		    && args[3].isInt()			// X
		    && args[4].isInt()			// Y
		    && args[5].isString()		// Max object name (followed by optional arguments)
		   ) {
		    itsUpstreamName = args[0].getString();
		    itsScriptingName = args[1].getString();
		    itsDownstreamName = args[2].getString();
		    itsX = args[3].getInt();
		    itsY = args[4].getInt();
		    itsObjectName = args[5].getString();
		    itsArgs = rewriteAttributes(Atom.removeFirst(args, 6));
		    
		    declareIO(1, 0);	//	Command inlet only.
		    
		    setInletAssist(0, "'create' or 'delete'");
		    createInfoOutlet(false);

		    post("Instantiated: " + Atom.toOneString(args));
		} else {
		    bail("Instantiate:  " + Atom.toOneString(args));
		}
    }
    
    /**	Rewrite any tokens "--attr" as "@attr". */

    private Atom[] rewriteAttributes(Atom[] args) {
        Atom[] result = new Atom[args.length];
        
        for (int i = 0; i < args.length; i++) {
            Atom a = args[i];
            
            if (a.isString() && a.getString().startsWith("--")) {
                result[i] = Atom.newAtom("@" + a.getString().substring(2));
            } else {
                result[i] = a;
            }
        }
        
        return result;
    }

    public void create() {
		MaxPatcher p = getParentPatcher();
		p.send("window", Atom.parse("setfont Monaco 9."));
		MaxBox b = p.newDefault(itsX, itsY, itsObjectName, itsArgs);
		MaxBox upstream00, downstream00;
		b.setName(itsScriptingName);
	
		if (!VOID_NAME.equals(itsUpstreamName)) {
			upstream00 = p.getNamedBox(itsUpstreamName);
		
			if (upstream00 == null) {
			    error("Instantiate: cannot find " + itsUpstreamName);
			} else {
			    p.connect(upstream00, 0, b, 0);
			}
		}

		if (!VOID_NAME.equals(itsDownstreamName)) {
			downstream00 = p.getNamedBox(itsDownstreamName);
		
			if (downstream00 == null) {
			    error("Instantiate: cannot find " + itsDownstreamName);
			} else {
			    p.connect(b, 0, downstream00, 0);
			}
		}
    }

    public void delete() {
		MaxPatcher p = getParentPatcher();
		MaxBox b00 = p.getNamedBox(itsScriptingName);
	
		if (b00 != null) {
		    b00.remove();
		} else {
		    error("Instantiate: " + itsScriptingName + " not found");
		}
    }
}
