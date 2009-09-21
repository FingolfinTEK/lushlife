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
package org.lushlife.stla.level;

import java.lang.annotation.Annotation;
import java.util.EnumMap;

import org.lushlife.stla.Level;
import org.lushlife.stla.spi.LevelResolver;
import org.lushlife.stla.spi.LogLevelBinding;
import org.lushlife.stla.util.Closure;
import org.lushlife.stla.util.ConcurrentEnumMapCache;
import org.lushlife.stla.util.Reflections;


/**
 * @author Takeshi Kondo
 */
public class AnnotationLevelResolver implements LevelResolver {

	private ConcurrentEnumMapCache<Level> cache = new ConcurrentEnumMapCache<Level>();
	private Level defaultLogLevel = Level.INFO;

	public void setDefaultLogLevel(String defaultLogLevel) {
		this.defaultLogLevel = Level.valueOf(defaultLogLevel.toUpperCase());
	}

	public <T extends Enum<T>> Level toLevel(final T logId) {
		return cache.putIfAbsent(logId, new Closure<EnumMap<T, Level>>() {
			public EnumMap<T, Level> call() {
				return createEnumMap(logId.getDeclaringClass());
			}
		});
	}

	private <T extends Enum<T>> EnumMap<T, Level> createEnumMap(
			final Class<T> clazz) {
		EnumMap<T, Level> map = new EnumMap<T, Level>(clazz);
		for (T t : clazz.getEnumConstants()) {
			Level level = resolveLevel(t);
			map.put(t, level);
		}
		return map;
	}

	private Level resolveLevel(Enum<?> id) {
		Annotation[] annotations = Reflections.getAnnotations(id);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAnnotationPresent(
					LogLevelBinding.class)) {
				return annotation.annotationType().getAnnotation(
						LogLevelBinding.class).value();
			}
		}
		return defaultLogLevel;
	}

}
