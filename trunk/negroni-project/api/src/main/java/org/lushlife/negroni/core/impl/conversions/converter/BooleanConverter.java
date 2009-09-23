package org.lushlife.negroni.core.impl.conversions.converter;

import org.lushlife.negroni.core.impl.conversions.Converter;

public class BooleanConverter implements Converter<Boolean> {

	public Boolean convert(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		return Boolean.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Boolean.class)) {
			return true;
		}
		if (from.equals(boolean.class)) {
			return true;
		}
		return false;
	}
}
