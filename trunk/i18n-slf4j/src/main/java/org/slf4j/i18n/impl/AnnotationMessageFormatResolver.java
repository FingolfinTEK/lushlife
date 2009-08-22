package org.slf4j.i18n.impl;

import java.lang.annotation.Annotation;
import java.util.EnumMap;
import java.util.concurrent.Callable;

import org.slf4j.i18n.collections.ConcurrentEnumCache;
import org.slf4j.i18n.helpers.ReflectionHelper;
import org.slf4j.i18n.spi.LogMessageFormatResolver;

/**
 * resolve log message format form annotation associated with enums.
 * 
 * @author Takeshi Kondo
 */
public class AnnotationMessageFormatResolver implements
		LogMessageFormatResolver {

	ConcurrentEnumCache<String> cache = new ConcurrentEnumCache<String>();

	public <E extends Enum<E>> String toMessageFormat(final E logId) {
		return cache.putIfAbsent(logId, new Callable<EnumMap<E, String>>() {
			public EnumMap<E, String> call() throws Exception {
				return createMessageMap(logId.getDeclaringClass());
			}
		});
	}

	private <E extends Enum<E>> EnumMap<E, String> createMessageMap(
			Class<E> clazz) {
		EnumMap<E, String> map = new EnumMap<E, String>(clazz);
		for (E e : clazz.getEnumConstants()) {
			Annotation logLevel = ReflectionHelper.getLogLevelAnnotation(e);
			if (logLevel != null) {
				map.put(e, (String) ReflectionHelper.getValue(logLevel));
			}
		}
		return map;

	}
}
