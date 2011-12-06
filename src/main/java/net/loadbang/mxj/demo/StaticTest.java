package net.loadbang.mxj.demo;

import com.cycling74.max.*;

/** A little test object to show the behaviour of the MXJ
    classloader. */

public class StaticTest extends MaxObject {
    public StaticTest() {
        Counter.bump();

		createInfoOutlet(false);
		declareTypedIO("A","I");		//	bang in, int out
    }
    
    @Override
	public void bang() {
        outlet(0, Counter.value());
    }
}
