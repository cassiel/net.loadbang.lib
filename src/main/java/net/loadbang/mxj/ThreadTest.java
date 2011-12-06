//	$Id$
//	$Source$

package net.loadbang.mxj;

import com.cycling74.max.*;

public class ThreadTest extends MaxObject implements Runnable {
    private MaxQelem itsQ;
    private MaxClock itsC;
    
    public ThreadTest() {
        itsQ = new MaxQelem(this, "bonk");
        itsC = new MaxClock(this, "bonk");
    }
    
    @Override
	protected void notifyDeleted() {
        itsQ.release();
        itsC.release();
    }
    
    public void bonk_deferred() {
        itsQ.set();
    }
    
    public void bonk_clocked() {
        itsC.delay(1000);
    }

    public void bonk() {
        Thread t = new Thread(this);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }
    
    public void run() {
        post("bonk 1");
        try { Thread.sleep(5000); } catch (InterruptedException _) { }
        post("bonk 2");
    }
}
