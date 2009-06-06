package lushfile.core.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
	Map<K, V> map = new HashMap<K, V>();

	public MapBuilder<K, V> put(K str, V value) {
		map.put(str, value);
		return this;
	}

	public Map<K, V> toMap() {
		return map;
	}
}
