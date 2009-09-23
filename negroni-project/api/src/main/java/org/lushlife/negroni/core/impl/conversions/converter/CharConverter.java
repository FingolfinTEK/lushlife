package org.lushlife.negroni.core.impl.conversions.converter;

import org.lushlife.negroni.core.impl.conversions.Converter;

public class CharConverter implements Converter<Character> {

	public Character convert(Object obj) {
		return (Character) obj;
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Character.class)) {
			return true;
		}
		if (from.equals(char.class)) {
			return true;
		}
		return false;
	}

}
