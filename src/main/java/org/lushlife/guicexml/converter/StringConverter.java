package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;



public class StringConverter implements Converter<String> {

	@Override
	public String convert(String str) {
		return str;
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { String.class, CharSequence.class };
	}

}
