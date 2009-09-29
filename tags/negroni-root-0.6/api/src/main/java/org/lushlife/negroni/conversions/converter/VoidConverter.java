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
public class VoidConverter implements Converter<Object> {

	public Object convert(Object obj) {
		if (obj != null) {
			throw new RuntimeException();
		}
		return null;
	}

	public boolean isAssignableTo(Class<?> from) {
		return from.equals(Object.class);
	}

}