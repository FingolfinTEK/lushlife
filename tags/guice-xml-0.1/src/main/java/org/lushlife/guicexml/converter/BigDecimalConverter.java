package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.lushlife.guicexml.spi.Converter;

class BigDecimalConverter implements Converter<DateFormat> {

	@Override
	public DateFormat convert(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { DateFormat.class };
	}

}
