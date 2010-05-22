package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;



public class CharConverter implements Converter<Character> {

	@Override
	public Character convert(String str) {
		if (str.length() != 0) {
			// TODO
			throw new IllegalArgumentException("char length " + str.length());
		}
		return str.charAt(0);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { char.class, Character.class };
	}

}
