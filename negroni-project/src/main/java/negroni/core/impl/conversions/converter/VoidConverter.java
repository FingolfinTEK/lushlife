package negroni.core.impl.conversions.converter;

import negroni.core.impl.conversions.Converter;

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
