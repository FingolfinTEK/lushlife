/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.lushlife.negroni.conversions;

import java.util.HashMap;
import java.util.Map;

import org.lushlife.negroni.conversions.converter.BooleanConverter;
import org.lushlife.negroni.conversions.converter.ByteConverter;
import org.lushlife.negroni.conversions.converter.CharConverter;
import org.lushlife.negroni.conversions.converter.IntegerConverter;
import org.lushlife.negroni.conversions.converter.LongConverter;
import org.lushlife.negroni.conversions.converter.NumberConverter;
import org.lushlife.negroni.conversions.converter.ObjectConverter;
import org.lushlife.negroni.conversions.converter.ShortConverter;
import org.lushlife.negroni.conversions.converter.VoidConverter;

/**
 * @author Takeshi Kondo
 */
public class Conversions {

	static Map<Class<?>, Converter<?>> converters = new HashMap<Class<?>, Converter<?>>();

	static public Map<Class<?>, Converter<?>> getConverters() {
		return converters;
	}

	static {
		getConverters().put(Boolean.class, new BooleanConverter());
		getConverters().put(boolean.class, new BooleanConverter());

		getConverters().put(char.class, new CharConverter());
		getConverters().put(Character.class, new CharConverter());

		getConverters().put(Byte.class, new ByteConverter());
		getConverters().put(byte.class, new ByteConverter());

		getConverters().put(Short.class, new ShortConverter());
		getConverters().put(short.class, new ShortConverter());

		getConverters().put(Integer.class, new IntegerConverter());
		getConverters().put(int.class, new IntegerConverter());

		getConverters().put(Long.class, new LongConverter());
		getConverters().put(long.class, new LongConverter());

		getConverters().put(Number.class, new NumberConverter());

		getConverters().put(void.class, new VoidConverter());
		getConverters().put(Object.class, new ObjectConverter());

	}

	static public boolean isConvert(Class<?> from, Class<?> to) {
		if (to == null) {
			return false;
		}
		if (to.isAssignableFrom(from)) {
			return true;
		}
		Converter<?> converter = converters.get(to);
		if (converter == null) {
			return false;
		}
		return converter.isAssignableTo(from);
	}

}
