//	$Id$
//	$Source$

package net.loadbang.reflect;

public class Foo {
	public void foo(int x) { System.out.println("[INT " + x + "]"); }
	public void foo(float x) { System.out.println("[FLOAT " + x + "]"); }
	public void foo(String x) { System.out.println("[STRING " + x + "]"); }
	//public void foo(int x, float y) { System.out.println("[INT " + x + ", FLOAT " + y + "]"); }
	public void foo(int x, int y) { System.out.println("[INT " + x + ", INT " + y + "]"); }
	//public void foo(int[] x) { System.out.println("[INT ARRAY]"); }
	public void foo(float[] x) { System.out.println("[FLOAT ARRAY]"); }
}
