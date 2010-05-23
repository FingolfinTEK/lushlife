package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import org.lushlife.guicexml.spi.Converter;

class StringConverter implements Converter<String> {

	@Override
	public String convert(String str) {
		return str;
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { String.class, CharSequence.class };
	}

}
