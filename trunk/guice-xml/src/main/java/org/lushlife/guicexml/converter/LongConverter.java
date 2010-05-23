package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import org.lushlife.guicexml.spi.Converter;

class LongConverter implements Converter<Long> {

	@Override
	public Long convert(String str) {
		if (str == null) {
			return null;
		}
		return Long.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { long.class, Long.class };
	}
}
