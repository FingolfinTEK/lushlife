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
package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

/**
 * @author Takeshi Kondo
 */
public class NumberConverter implements Converter<Number> {

	Converter<?>[] converters = new Converter<?>[] { new ByteConverter(),
			new ShortConverter(), new IntegerConverter(), new LongConverter(),
			new FloatConverter(), new DoubleConverter() };

	public Number convert(Object obj) {
		for (Converter<?> c : converters) {
			if (c.isAssignableTo(obj.getClass())) {
				return (Number) c.convert(obj);
			}
		}

		return null;
	}

	public boolean isAssignableTo(Class<?> from) {
		for (Converter<?> c : converters) {
			if (c.isAssignableTo(from)) {
				return true;
			}
		}
		return false;
	}

}
