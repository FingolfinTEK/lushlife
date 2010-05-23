package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

import org.lushlife.guicexml.spi.Converter;

class PatternConverter implements Converter<Pattern> {

	@Override
	public Pattern convert(String pattern) {
		if (pattern == null) {
			return null;
		}
		return Pattern.compile(pattern);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { Pattern.class };
	}

}
