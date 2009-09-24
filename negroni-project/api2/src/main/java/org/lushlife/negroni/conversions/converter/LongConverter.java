package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

public class LongConverter implements Converter<Long> {

	public Long convert(Object obj) {
		if (obj instanceof Long) {
			return (Long) obj;
		}
		return Long.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Long.class)) {
			return true;
		}
		if (from.equals(long.class)) {
			return true;
		}
		return false;
	}

}
