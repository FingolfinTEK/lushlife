package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.lushlife.guicexml.spi.Converter;

class DateformatConverter implements Converter<BigDecimal> {

	@Override
	public BigDecimal convert(String str) {
		if (str == null) {
			return null;
		}
		return new BigDecimal(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { BigDecimal.class };
	}

}
