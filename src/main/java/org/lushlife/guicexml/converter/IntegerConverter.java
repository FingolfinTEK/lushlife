package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;



public class IntegerConverter implements Converter<Integer> {

	@Override
	public Integer convert(String str) {
		return Integer.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { int.class, Integer.class };
	}
}
