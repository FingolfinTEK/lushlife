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
package org.lushlife.negroni.guice;

import java.lang.annotation.Annotation;

import org.lushlife.negroni.Container;

import com.google.inject.Injector;
import com.google.inject.ScopeAnnotation;

/**
 * @author Takeshi Kondo
 */
public class GuiceContainer implements Container {
	private Injector injector;

	public GuiceContainer(Injector injector) {
		this.injector = injector;
	}

	public <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	public boolean isManagementScope(Class<?> clazz) {
		for (Annotation annotation : clazz.getAnnotations()) {
			if (annotation.annotationType().isAnnotationPresent(
					ScopeAnnotation.class)) {
				return true;
			}
		}
		return false;
	}

}
