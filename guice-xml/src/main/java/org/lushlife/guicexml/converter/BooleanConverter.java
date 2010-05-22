package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;



public class BooleanConverter implements Converter<Boolean> {

	@Override
	public Boolean convert(String str) {
		return Boolean.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { boolean.class, Boolean.class };
	}

}