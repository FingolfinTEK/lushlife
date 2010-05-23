package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import org.lushlife.guicexml.spi.Converter;

class IntegerConverter implements Converter<Integer> {

	@Override
	public Integer convert(String str) {
		if (str == null) {
			return null;
		}
		return Integer.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { int.class, Integer.class };
	}
}
