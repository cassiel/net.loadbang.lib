//	$Id$
//	$Source$

package net.loadbang.util;

/** Library. */

public class RandLib {
	static public int random(int limit) {
		return (int) (Math.random() * limit);
	}
	
	static public boolean coin() {
		return random(2) == 1;
	}
}
