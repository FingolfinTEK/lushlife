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
package com.google.code.lushlife.stla.util;

import java.lang.annotation.Annotation;

/**
 * @author Takeshi Kondo
 */
public class Reflections {

	/**
	 * 
	 * @param annotation
	 * @return
	 */
	static public Object getValue(Annotation annotation) {
		try {
			return annotation.annotationType().getMethod("value").invoke(
					annotation);
		} catch (Exception e1) {
			LogLog.reportFailure("Failed to get value " + annotation, e1);
		}
		return null;
	}

	/**
	 * return annotations associated with enums
	 */
	static public Annotation[] getAnnotations(Enum<?> e) {
		try {
			return e.getDeclaringClass().getDeclaredField(e.name())
					.getAnnotations();
		} catch (Exception e1) {
			LogLog.reportFailure("Failed to get annotations  " + e, e1);
		}
		return null;
	}

}
