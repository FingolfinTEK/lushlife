package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

public class IntegerConverter implements Converter<Integer> {
	public Integer convert(Object obj) {
		if (obj instanceof Integer) {
			return (Integer) obj;
		}
		return Integer.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Integer.class)) {
			return true;
		}
		if (from.equals(int.class)) {
			return true;
		}
		return false;
	}

}
