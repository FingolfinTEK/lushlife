/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.google.code.lushlife.stla.message;

import java.lang.annotation.Annotation;
import java.util.EnumMap;
import java.util.Locale;

import com.google.code.lushlife.stla.spi.LogLevelBinding;
import com.google.code.lushlife.stla.spi.MessageResolver;
import com.google.code.lushlife.stla.util.Closure;
import com.google.code.lushlife.stla.util.ConcurrentEnumMapCache;
import com.google.code.lushlife.stla.util.Reflections;

/**
 * @author Takeshi Kondo
 */
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
