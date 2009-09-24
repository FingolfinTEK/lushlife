package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

public class ByteConverter implements Converter<Byte> {

	public Byte convert(Object obj) {
		if (obj instanceof Byte) {
			return (Byte) obj;
		}
		return Byte.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Byte.class)) {
			return true;
		}
		if (from.equals(byte.class)) {
			return true;
		}
		return false;
	}

}
