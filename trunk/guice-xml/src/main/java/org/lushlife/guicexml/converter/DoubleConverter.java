package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import org.lushlife.guicexml.spi.Converter;

class DoubleConverter implements Converter<Double> {

	@Override
	public Double convert(String str) {
		return Double.valueOf(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { double.class, Double.class };
	}
}
