package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

public interface Converter<T> {
	T convert(String str);

	Type[] getTypes();
}
