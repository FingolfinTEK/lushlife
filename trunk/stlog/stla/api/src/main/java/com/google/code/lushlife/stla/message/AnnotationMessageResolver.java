package com.google.code.lushlife.stla.message;

import java.lang.annotation.Annotation;
import java.util.EnumMap;
import java.util.Locale;

import com.google.code.lushlife.stla.spi.LogLevelBinding;
import com.google.code.lushlife.stla.spi.MessageResolver;
import com.google.code.lushlife.stla.util.Closure;
import com.google.code.lushlife.stla.util.ConcurrentEnumMapCache;
import com.google.code.lushlife.stla.util.Reflections;


public class AnnotationMessageResolver implements MessageResolver {

	private ConcurrentEnumMapCache<String> cache = new ConcurrentEnumMapCache<String>();

	public <T extends Enum<T>> String toMessage(final T logId, Locale locale) {
		return cache.putIfAbsent(logId, new Closure<EnumMap<T, String>>() {
			public EnumMap<T, String> call() {
				return createEnumMap(logId.getDeclaringClass());
			}
		});
	}

	public <T extends Enum<T>> EnumMap<T, String> createEnumMap(
			final Class<T> clazz) {
		EnumMap<T, String> map = new EnumMap<T, String>(clazz);
		for (T t : clazz.getEnumConstants()) {
			String level = resolveMessage(t);
			map.put(t, level);
		}
		return map;
	}

	public String resolveMessage(Enum<?> id) {
		Annotation[] annotations = Reflections.getAnnotations(id);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAnnotationPresent(
					LogLevelBinding.class)) {
				return String.valueOf(Reflections.getValue(annotation));
			}
		}
		return null;
	}

}
