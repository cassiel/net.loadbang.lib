//	$Id$
//	$Source$

package net.loadbang.util;

/**	Pair of arbitrary object types. Either may be null. (We should probably use the
 	"00" notation here, but it's not really worth it for a pure datatype.) */

public class Pair<A, B> {
	private A itsFst;
	private B itsSnd;

	public Pair(A fst, B snd) {
		itsFst = fst;
		itsSnd = snd;
	}
	
	public void setFst(A fst) {
		itsFst = fst;
	}
	
	public void setSnd(B snd) {
		itsSnd = snd;
	}

	public A getFst() {
		return itsFst;
	}
	
	public B getSnd() {
		return itsSnd;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itsFst == null) ? 0 : itsFst.hashCode());
		result = prime * result + ((itsSnd == null) ? 0 : itsSnd.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Pair<A, B> other = (Pair<A, B>) obj;
		if (itsFst == null) {
			if (other.itsFst != null)
				return false;
		} else if (!itsFst.equals(other.itsFst))
			return false;
		if (itsSnd == null) {
			if (other.itsSnd != null)
				return false;
		} else if (!itsSnd.equals(other.itsSnd))
			return false;
		return true;
	}

	@Override public String toString() {
		return "(" + itsFst + ", " + itsSnd + ")";
	}
}
