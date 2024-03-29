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
public class DoubleConverter implements Converter<Double> {

	public Double convert(Object obj) {
		if (obj instanceof Double) {
			return (Double) obj;
		}
		return Double.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Double.class)) {
			return true;
		}
		if (from.equals(double.class)) {
			return true;
		}
		return false;
	}

}
