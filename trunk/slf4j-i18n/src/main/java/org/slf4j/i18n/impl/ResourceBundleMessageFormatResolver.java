package org.slf4j.i18n.impl;

import java.util.EnumMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import org.slf4j.i18n.collections.ConcurrentEnumMapCache;
import org.slf4j.i18n.spi.LogMessageFormatResolver;

/**
 * The resolve ResourceBundleMessageFormatResolver log message format by
 * ResourceBundle associated with enum class. Enum class name is used for
 * ResourceBundle name.
 * 
 * for instance, if enum class is "example.LogMessages", loaded ResourceBundle
 * file is "example/LogMessages.properties".
 * 
 * @author Takeshi Kondo
 * 
 */
public class ResourceBundleMessageFormatResolver implements
		LogMessageFormatResolver {
	ConcurrentEnumMapCache<String> cache = new ConcurrentEnumMapCache<String>();

	public <E extends Enum<E>> String toMessageFormat(final E logId) {
		return cache.putIfAbsent(logId, new Callable<EnumMap<E, String>>() {

			public EnumMap<E, String> call() throws Exception {
				return createMessageMap(logId.getDeclaringClass());
			}

		});
	}

	public <E extends Enum<E>> EnumMap<E, String> createMessageMap(
			Class<E> clazz) {
		EnumMap<E, String> map = new EnumMap<E, String>(clazz);

		// TODO Which ClassLoader does we select ?
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(clazz
					.getName(), Locale.getDefault(), Thread.currentThread()
					.getContextClassLoader());
			for (E e : clazz.getEnumConstants()) {
				try {
					map.put(e, resourceBundle.getString(e.name()));
				} catch (MissingResourceException e1) {
				}
			}
		} catch (MissingResourceException e1) {
		}

		return map;

	}

}
