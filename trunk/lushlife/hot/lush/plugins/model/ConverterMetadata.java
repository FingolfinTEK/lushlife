package lush.plugins.model;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class ConverterMetadata {

	Map<Class<?>, Class<? extends PropertyConverter<?>>> converters = new HashMap<Class<?>, Class<? extends PropertyConverter<?>>>();

	public Map<Class<?>, Class<? extends PropertyConverter<?>>> getConverters() {
		return converters;
	}

	public <T> void addConverter(Class<T> clazz,
			Class<? extends PropertyConverter<T>> converter) {
		converters.put(clazz, converter);
	}

	@Inject
	Injector injector;

	public PropertyConverter<?> getConverter(Class<?> type) {
		Class<? extends PropertyConverter<?>> converter = converters.get(type);
		if (converter == null) {
			return null;
		}
		return injector.getInstance(converter);
	}

}
