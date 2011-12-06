package net.loadbang.mxj.demo;

public class Counter {
    private static int theCounter = 0;

    	public static void bump() {
    	    ++theCounter;
    	}
    	
    	public static int value() {
    	    return theCounter;
    	}
}
