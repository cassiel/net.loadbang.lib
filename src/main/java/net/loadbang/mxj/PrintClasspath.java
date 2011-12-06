/* $Id$ */

package net.loadbang.mxj;

import com.cycling74.max.*;

/**
 *
 * outputs the classpath
 * 
 * created on 23-Apr-2004
 * @author bbn
 */
public class PrintClasspath extends MaxObject {
	
	boolean auto = false;
	
	PrintClasspath() {
		declareAttribute("auto");
		createInfoOutlet(false);
		declareTypedIO("A","MM");
	}
	
	@Override
	protected void loadbang() {
		if (auto) bang();
	}

	@Override
	public void bang() {
		output(0,MaxSystem.getClassPath());
		output(1,MaxSystem.getSystemClassPath());
	}
	
	private void output(int index,String[] s) {
		for (int i=0;i<s.length;i++)
			outlet(index, s[i]);
	}
}
