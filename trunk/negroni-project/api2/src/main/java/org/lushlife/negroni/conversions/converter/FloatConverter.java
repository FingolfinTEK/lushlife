package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

public class FloatConverter implements Converter<Float> {

	public Float convert(Object obj) {
		if (obj instanceof Float) {
			return (Float) obj;
		}
		return Float.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Float.class)) {
			return true;
		}
		if (from.equals(float.class)) {
			return true;
		}
		return false;
	}

}
