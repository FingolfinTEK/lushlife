package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

public class ObjectConverter implements Converter<Object> {

	public Object convert(Object obj) {
		return obj;
	}

	public boolean isAssignableTo(Class<?> from) {
		return true;
	}

}
