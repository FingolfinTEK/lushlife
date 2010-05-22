package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;



public class LongConverter implements Converter<Long> {

	@Override
	public Long convert(String str) {
		return Long.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { long.class, Long.class };
	}
}
