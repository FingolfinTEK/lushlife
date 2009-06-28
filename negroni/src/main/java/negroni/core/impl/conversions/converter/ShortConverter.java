package negroni.core.impl.conversions.converter;

import negroni.core.impl.conversions.Converter;

public class ShortConverter implements Converter<Short> {

	public Short convert(Object obj) {
		if (obj instanceof Short) {
			return (Short) obj;
		}
		return Short.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Short.class)) {
			return true;
		}
		if (from.equals(short.class)) {
			return true;
		}
		return false;
	}

}
