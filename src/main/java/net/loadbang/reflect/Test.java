//	$Id$
//	$Source$

package net.loadbang.reflect;

import net.loadbang.reflect.exn.Dispatch;
import com.cycling74.max.Atom;

/**	Testing examination of methods. */

public class Test {
	public static void main(String[] args) {
		Foo foo = new Foo();
		Dispatcher dispatcher = new Dispatcher(foo);
		
		try {
			dispatcher.dispatch("foo", Atom.parse("457 56 9"));
		} catch (Dispatch exn) {
			System.err.println("Dispatch: " + exn);
		}
	}
}
