//	$Id$
//	$Source$

package net.loadbang.util;

/**	Triple of arbitrary object types. Either may be null. (We should probably use the
 	"00" notation here, but it's not really worth it for a pure datatype.) */

public class Triple<A, B, C> {
	private A itsFst;
	private B itsSnd;
	private C itsThd;

	public Triple(A fst, B snd, C thd) {
		itsFst = fst;
		itsSnd = snd;
		itsThd = thd;
	}
	
	public void setFst(A fst) {
		itsFst = fst;
	}
	
	public void setSnd(B snd) {
		itsSnd = snd;
	}

	public void setThd(C thd) {
		itsThd = thd;
	}

	public A getFst() {
		return itsFst;
	}
	
	public B getSnd() {
		return itsSnd;
	}
	
	public C getThd() {
		return itsThd;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itsFst == null) ? 0 : itsFst.hashCode());
		result = prime * result + ((itsSnd == null) ? 0 : itsSnd.hashCode());
		result = prime * result + ((itsThd == null) ? 0 : itsThd.hashCode());
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
		Triple<A, B, C> other = (Triple<A, B, C>) obj;
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
		if (itsThd == null) {
			if (other.itsThd != null)
				return false;
		} else if (!itsThd.equals(other.itsThd))
			return false;
		return true;
	}

	@Override public String toString() {
		return "(" + itsFst + ", " + itsSnd + ", " + itsThd + ")";
	}
}
