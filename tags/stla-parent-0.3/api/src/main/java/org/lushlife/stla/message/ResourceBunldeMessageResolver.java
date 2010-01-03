/*
 * Copyright 2009 Takeshi Kondo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lushlife.stla.message;

import java.util.EnumMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.lushlife.stla.spi.MessageResolver;
import org.lushlife.stla.util.Closure;
import org.lushlife.stla.util.ConcurrentEnumMapCache;


/**
 * @author Takeshi Kondo
 */
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
