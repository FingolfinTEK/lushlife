package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;


public class Conversions {

	static private Map<Type, Converter<?>> converters;
	static {
		initialize();

	}

	@SuppressWarnings("unchecked")
	static public void initialize() {
		converters = new ConcurrentHashMap<Type, Converter<?>>();
		addConverter(new BooleanConverter());
		addConverter(new ByteConverter());
		addConverter(new CharConverter());
		addConverter(new DoubleConverter());
		addConverter(new FloatConverter());
		addConverter(new IntegerConverter());
		addConverter(new LongConverter());
		addConverter(new StringConverter());
		ServiceLoader<Converter> loader = ServiceLoader.load(Converter.class);
		for (Converter<?> converter : loader) {
			addConverter(converter);
		}
	}

	public static Map<Type, Converter<?>> getConverters() {
		return converters;
	}

	public static void setConverters(Map<Type, Converter<?>> converters) {
		Conversions.converters = converters;
	}

	static public void addConverter(Converter<?> converter) {
		for (Type type : converter.getTypes()) {
			converters.put(type, converter);
		}
	}

	static public Converter<?> getConverter(Type type) {
		if (!converters.containsKey(type)) {
			throw new IllegalStateException("converter not found " + type);
		}
		return converters.get(type);
	}

}
