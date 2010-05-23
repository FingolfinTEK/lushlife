package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import org.lushlife.guicexml.spi.Converter;

class FloatConverter implements Converter<Float> {

	@Override
	public Float convert(String str) {
		if (str == null) {
			return null;
		}
		return Float.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { float.class, Float.class };
	}
}
