package stlog.util;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentEnumMapCache<V> {

	ReentrantLock lock = new ReentrantLock();
	Map<Class<? extends Enum<?>>, EnumMap<?, V>> cache = new ConcurrentHashMap<Class<? extends Enum<?>>, EnumMap<?, V>>();

	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> V putIfAbsent(Enum<?> key,
			Closure<EnumMap<E, V>> callable) {

		Class<? extends Enum<?>> clazz = key.getDeclaringClass();
		EnumMap<?, V> enumMap = cache.get(clazz);
		if (enumMap == null) {
			lock.lock();
			try {
				enumMap = cache.get(clazz);
				if (enumMap == null) {
					enumMap = callable.call();
					cache.put(clazz, enumMap);
				}

			} finally {
				lock.unlock();
			}
		}
		return enumMap.get(key);
	}

	public void clear() {
		this.cache.clear();
	}

}