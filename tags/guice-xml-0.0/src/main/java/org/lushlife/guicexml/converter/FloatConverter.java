package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;



public class FloatConverter implements Converter<Float> {

	@Override
	public Float convert(String str) {
		return Float.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { float.class, Float.class };
	}
}
