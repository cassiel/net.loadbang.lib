package net.loadbang.spring;

import org.springframework.context.support.AbstractApplicationContext;

/**	Hashtable entry for a context with a reference count. */

public class ContextHashEntry {
	private int itsRefCount;
	private AbstractApplicationContext itsContext;

	public ContextHashEntry(AbstractApplicationContext context) {
		this.itsRefCount = 1;
		this.itsContext = context;
	}
	
	public int getRefCount() {
		return itsRefCount;
	}
	
	public AbstractApplicationContext getContext() {
		return itsContext;
	}
	
	public void bumpRefCount() {
		itsRefCount++;
	}

	public void dropRefCount() {
		itsRefCount--;
	}

	public boolean noRefs() {
		return itsRefCount == 0;
	}
}
