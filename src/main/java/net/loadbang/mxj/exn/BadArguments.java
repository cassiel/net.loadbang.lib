/* $Id$ */

package net.loadbang.mxj.exn;

import com.cycling74.max.*;

public class BadArguments extends Exception {
    Atom[] itsArgs;

    public BadArguments(Atom[] args) {
	itsArgs = args;
    }

    @Override
	public String toString() {
	return "Bad arguments: " + Atom.toOneString(itsArgs);
    }
}
