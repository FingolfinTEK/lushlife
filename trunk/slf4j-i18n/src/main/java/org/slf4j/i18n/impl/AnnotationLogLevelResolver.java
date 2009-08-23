package org.slf4j.i18n.impl;

import java.lang.annotation.Annotation;
import java.util.EnumMap;
import java.util.concurrent.Callable;

import org.slf4j.i18n.LogLevel;
import org.slf4j.i18n.collections.ConcurrentEnumCache;
import org.slf4j.i18n.helpers.ReflectionHelper;
import org.slf4j.i18n.spi.LogBindigType;
import org.slf4j.i18n.spi.LogLevelResolver;

/**
 * The AnnotationLogLevelResolver resolve log level by annotation associated
 * with enums.
 * 
 * @author Takeshi Kondo
 */
public class AnnotationLogLevelResolver implements LogLevelResolver {

	ConcurrentEnumCache<LogLevel> cache = new ConcurrentEnumCache<LogLevel>();

	private <E extends Enum<E>> EnumMap<E, LogLevel> createLogLevelMap(
			Class<E> clazz) {
		EnumMap<E, LogLevel> map = new EnumMap<E, LogLevel>(clazz);
		for (E e : clazz.getEnumConstants()) {
			Annotation logLevel = ReflectionHelper.getLogLevelAnnotation(e);
			if (logLevel != null
					&& logLevel.annotationType().isAnnotationPresent(
							LogBindigType.class)) {
				LogBindigType logBindigType = logLevel.annotationType()
						.getAnnotation(LogBindigType.class);
				map.put(e, logBindigType.value());
			}
		}
		return map;

	}

	public <E extends Enum<E>> LogLevel toLogLevel(final E logId) {
		return cache.putIfAbsent(logId, new Callable<EnumMap<E, LogLevel>>() {
			public EnumMap<E, LogLevel> call() throws Exception {
				return createLogLevelMap(logId.getDeclaringClass());
			}
		});
	}
}
