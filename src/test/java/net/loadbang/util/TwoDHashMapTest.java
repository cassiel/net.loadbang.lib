package net.loadbang.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TwoDHashMapTest {
	@Test
	public void remove2D() {
		TwoDHashMap<Integer, Integer, String> map =
			new TwoDHashMap<Integer, Integer, String>();
		
		assertNull(map.get00(1, 2));
		map.put(1, 2, "HELLO");
		assertEquals("HELLO", map.get00(1, 2));
		map.remove(1, 2);
		assertNull(map.get00(1, 2));
	}

	@Test
	public void secondLayer() {
		TwoDHashMap<Integer, Integer, String> map =
			new TwoDHashMap<Integer, Integer, String>();
		
		assertNull(map.get00(1, 2));
		assertNull(map.get00(1, 3));
		map.put(1, 2, "HELLO-2");
		map.put(1, 3, "HELLO-3");
		assertEquals("HELLO-2", map.get00(1, 2));
		map.remove(1, 2);
		assertEquals("HELLO-3", map.get00(1, 3));
	}
}
