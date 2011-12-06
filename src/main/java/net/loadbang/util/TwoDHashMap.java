//	$Id$
//	$Source$

package net.loadbang.util;

import java.util.HashMap;

/**	Simple two-dimensional HashMap. */

public class TwoDHashMap<K1, K2, V> {
	private HashMap<K1, HashMap<K2, V>> itsMap;
	
	public TwoDHashMap() {
		itsMap = new HashMap<K1, HashMap<K2, V>>();
	}
	
	public void put(K1 key1, K2 key2, V value) {
		HashMap<K2, V> m00 = itsMap.get(key1);
		
		if (m00 == null) {
			HashMap<K2, V> single = new HashMap<K2, V>();
			single.put(key2, value);
			itsMap.put(key1, single);
		} else {
			m00.put(key2, value);
		}
	}
	
	public V get00(K1 key1, K2 key2) {
		HashMap<K2, V> m00 = itsMap.get(key1);
		
		if (m00 == null) {
			return null;
		} else {
			return m00.get(key2);
		}
	}
	
	public void remove(K1 key1, K2 key2) {
		HashMap<K2, V> m00 = itsMap.get(key1);
		
		if (m00 != null) {
			m00.remove(key2);
			
			if (m00.size() == 0) {
				itsMap.remove(key1);
			}
		}
	}
}
