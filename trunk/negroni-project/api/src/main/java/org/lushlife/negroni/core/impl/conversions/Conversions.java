package org.lushlife.negroni.core.impl.conversions;

import java.util.HashMap;
import java.util.Map;

import org.lushlife.negroni.core.exceptions.NegroniException;
import org.lushlife.negroni.core.impl.conversions.converter.BooleanConverter;
import org.lushlife.negroni.core.impl.conversions.converter.ByteConverter;
import org.lushlife.negroni.core.impl.conversions.converter.CharConverter;
import org.lushlife.negroni.core.impl.conversions.converter.IntegerConverter;
import org.lushlife.negroni.core.impl.conversions.converter.LongConverter;
import org.lushlife.negroni.core.impl.conversions.converter.NumberConverter;
import org.lushlife.negroni.core.impl.conversions.converter.ObjectConverter;
import org.lushlife.negroni.core.impl.conversions.converter.ShortConverter;
import org.lushlife.negroni.core.impl.conversions.converter.VoidConverter;



public class Conversions {

	static Map<Class<?>, Converter<?>> converters = new HashMap<Class<?>, Converter<?>>();

	static public Map<Class<?>, Converter<?>> getConverters() {
		return converters;
	}

	static {
		getConverters().put(Boolean.class, new BooleanConverter());
		getConverters().put(boolean.class, new BooleanConverter());

		getConverters().put(char.class, new CharConverter());
		getConverters().put(Character.class, new CharConverter());

		getConverters().put(Byte.class, new ByteConverter());
		getConverters().put(byte.class, new ByteConverter());

		getConverters().put(Short.class, new ShortConverter());
		getConverters().put(short.class, new ShortConverter());

		getConverters().put(Integer.class, new IntegerConverter());
		getConverters().put(int.class, new IntegerConverter());

		getConverters().put(Long.class, new LongConverter());
		getConverters().put(long.class, new LongConverter());

		getConverters().put(Number.class, new NumberConverter());

		getConverters().put(void.class, new VoidConverter());
		getConverters().put(Object.class, new ObjectConverter());

	}

	//
	// static public void setConverters(Map<Class<?>, Converter<?>> converters)
	// {
	// Conversions.converters = converters;
	//	}

	static public boolean isConvert(Class<?> from, Class<?> to) {
		if (to.isAssignableFrom(from)) {
			return true;
		}
		Converter<?> converter = converters.get(to);
		if (converter == null) {
			return false;
		}
		return converter.isAssignableTo(from);
	}

	static public Object convertTo(Object from, Class<?> to) {
		if (to.isAssignableFrom(from.getClass())) {
			return from;
		}
		Converter<?> converter = converters.get(to);
		if (converter == null) {
			throw new NegroniException("can't convert from " + from + " to ="
					+ to);
		}
		if (!converter.isAssignableTo(from.getClass())) {
			throw new NegroniException("can't convert from " + from + " to ="
					+ to);
		}
		return converter.convert(from);
	}

}
