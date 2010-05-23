package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import org.lushlife.guicexml.spi.Converter;

class CharConverter implements Converter<Character> {

	@Override
	public Character convert(String str) {
		if (str.length() != 0) {
			// TODO message
			throw new IllegalArgumentException("char length " + str.length());
		}
		return str.charAt(0);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { char.class, Character.class };
	}

}
