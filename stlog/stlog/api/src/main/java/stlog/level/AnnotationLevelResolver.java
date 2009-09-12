package stlog.level;

import java.lang.annotation.Annotation;
import java.util.EnumMap;

import stlog.Level;
import stlog.spi.LevelResolver;
import stlog.spi.LogLevelBinding;
import stlog.util.Closure;
import stlog.util.ConcurrentEnumMapCache;
import stlog.util.Reflections;

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
