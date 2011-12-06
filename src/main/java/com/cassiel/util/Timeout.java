/*	$Id$
	$Source$
 */

package com.cassiel.util;

abstract public class Timeout implements Runnable {
    private int itsDelay, itsPoll;
    private boolean itsComplete;
    private Exception itsException00;

    public Timeout(int delay, int poll) {
        itsDelay = delay;
        itsPoll = poll;
    }
    
    /*  We can't signal an exception (since our main body is
        in a sub-thread), but we can plant it and check
        it later. */
    
    protected void plantException(Exception exn) {
        itsException00 = exn;
    }
    
    abstract public void doit();

    public void run() {
        doit();
        itsComplete = true;
    }

    public boolean doneInTime()
        throws Exception
    {
        long startTime = System.currentTimeMillis();
        Thread t = new Thread(this);

        itsComplete = false;
        itsException00 = null;
        
        t.setDaemon(true);
        t.start();
        
        while (   !itsComplete
               && System.currentTimeMillis() < startTime + itsDelay
              ) {
            //System.out.println("Doze... " + (System.currentTimeMillis() - startTime));
            Thread.sleep(itsPoll);
        }

        if (itsException00 != null) {
            throw itsException00;
        } else {
            return itsComplete;
        }
    }
}
