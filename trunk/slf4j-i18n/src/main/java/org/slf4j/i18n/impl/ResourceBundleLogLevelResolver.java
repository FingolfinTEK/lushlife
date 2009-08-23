package org.slf4j.i18n.impl;

import java.util.EnumMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import org.slf4j.i18n.LogLevel;
import org.slf4j.i18n.collections.ConcurrentEnumMapCache;
import org.slf4j.i18n.spi.LogLevelResolver;

/**
 * The ResourceBundleLogLevelResolver resolve log level by ResourceBundle
 * associated with enum class. Enum class name is used for ResourceBundle name.
 * 
 * for instance, if enum class is "example.LogMessages", loaded ResourceBundle
 * file is "example/LogMessages_level.properties".
 * 
 * @author Takeshi Kondo
 * 
 */
public class ResourceBundleLogLevelResolver implements LogLevelResolver {
	ConcurrentEnumMapCache<LogLevel> cache = new ConcurrentEnumMapCache<LogLevel>();

	public <E extends Enum<E>> LogLevel toLogLevel(final E logId) {
		return cache.putIfAbsent(logId, new Callable<EnumMap<E, LogLevel>>() {

			public EnumMap<E, LogLevel> call() throws Exception {
				return createMessageMap(logId.getDeclaringClass());
			}

		});
	}

	public <E extends Enum<E>> EnumMap<E, LogLevel> createMessageMap(
			Class<E> clazz) {
		EnumMap<E, LogLevel> map = new EnumMap<E, LogLevel>(clazz);

		try {
			// TODO Which ClassLoader does we select ?
			ResourceBundle resourceBundle = ResourceBundle.getBundle(clazz
					.getName()
					+ "_level", Locale.getDefault(), Thread.currentThread()
					.getContextClassLoader());
			for (E e : clazz.getEnumConstants()) {
				try {
					map.put(e, LogLevel.valueOf(resourceBundle.getString(
							e.name()).toUpperCase()));
				} catch (MissingResourceException e1) {
				}
			}
		} catch (MissingResourceException e1) {

		}
		return map;

	}

}
