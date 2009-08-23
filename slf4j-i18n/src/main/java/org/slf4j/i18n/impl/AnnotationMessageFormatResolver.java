package org.slf4j.i18n.impl;

import java.lang.annotation.Annotation;
import java.util.EnumMap;
import java.util.concurrent.Callable;

import org.slf4j.i18n.collections.ConcurrentEnumMapCache;
import org.slf4j.i18n.helpers.Reflections;
import org.slf4j.i18n.spi.LogMessageFormatResolver;

/**
 * The AnnotationMessageFormatReolver resolve log message format form annotation
 * associated with enums.
 * 
 * @author Takeshi Kondo
 */
public class AnnotationMessageFormatResolver implements
		LogMessageFormatResolver {

	ConcurrentEnumMapCache<String> cache = new ConcurrentEnumMapCache<String>();

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
			Annotation logLevel = Reflections.getLogLevelAnnotation(e);
			if (logLevel != null) {
				map.put(e, (String) Reflections.getValue(logLevel));
			}
		}
		return map;

	}
}
