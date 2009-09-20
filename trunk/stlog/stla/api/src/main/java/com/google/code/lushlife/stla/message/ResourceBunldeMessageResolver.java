package com.google.code.lushlife.stla.message;

import java.util.EnumMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.google.code.lushlife.stla.spi.MessageResolver;
import com.google.code.lushlife.stla.util.Closure;
import com.google.code.lushlife.stla.util.ConcurrentEnumMapCache;


public class ResourceBunldeMessageResolver implements MessageResolver {
	private ConcurrentHashMap<Locale, ConcurrentEnumMapCache<String>> cache = new ConcurrentHashMap<Locale, ConcurrentEnumMapCache<String>>();
	private ReentrantLock lock = new ReentrantLock();

	public <E extends Enum<E>> String toMessage(final E logId,
			final Locale locale) {
		ConcurrentEnumMapCache<String> mapCache = cache.get(locale);
		if (mapCache == null) {
			lock.lock();
			try {
				mapCache = cache.get(locale);
				if (mapCache == null) {
					mapCache = new ConcurrentEnumMapCache<String>();
					cache.put(locale, mapCache);
				}
			} finally {
				lock.unlock();
			}
		}
		return mapCache.putIfAbsent(logId, new Closure<EnumMap<E, String>>() {

			public EnumMap<E, String> call() {
				return createEnuMap(logId.getDeclaringClass(), locale);
			}
		});
	}

	public <E extends Enum<E>> EnumMap<E, String> createEnuMap(Class<E> clazz,
			Locale locale) {
		EnumMap<E, String> map = new EnumMap<E, String>(clazz);
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(clazz.getName(),
					locale, Thread.currentThread().getContextClassLoader());
			for (E id : clazz.getEnumConstants()) {
				try {
					String key = id.name();
					map.put(id, bundle.getString(key));
				} catch (MissingResourceException e) {
				}
			}
		} catch (MissingResourceException e) {
		}
		return map;

	}

}
