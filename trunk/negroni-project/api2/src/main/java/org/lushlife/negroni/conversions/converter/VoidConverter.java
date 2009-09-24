package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

public class VoidConverter implements Converter<Object> {

	public Object convert(Object obj) {
		if (obj != null) {
			throw new RuntimeException();
		}
		return null;
	}

	public boolean isAssignableTo(Class<?> from) {
		return from.equals(Object.class);
	}

}
