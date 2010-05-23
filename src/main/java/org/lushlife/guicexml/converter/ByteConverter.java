package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import org.lushlife.guicexml.spi.Converter;

class ByteConverter implements Converter<Byte> {

	@Override
	public Byte convert(String str) {
		return Byte.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { byte.class, Byte.class };
	}
}
