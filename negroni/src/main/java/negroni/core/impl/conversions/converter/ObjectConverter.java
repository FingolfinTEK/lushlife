package negroni.core.impl.conversions.converter;

import negroni.core.impl.conversions.Converter;

public class ObjectConverter implements Converter<Object> {

	public Object convert(Object obj) {
		return obj;
	}

	public boolean isAssignableTo(Class<?> from) {
		return true;
	}

}
