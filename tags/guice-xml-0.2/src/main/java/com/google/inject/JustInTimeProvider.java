/*
 * Copyright 2010 the original author or authors.
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
package com.google.inject;

import java.lang.reflect.Method;

import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.spi.Dependency;

/**
 * @author Takeshi Kondo
 */
public class JustInTimeProvider<T> implements Provider<T> {
	static private Method getJustInTimeBinding;
	static {
		try {
			getJustInTimeBinding = InjectorImpl.class.getDeclaredMethod(
					"getJustInTimeBinding", Key.class, Errors.class);
			getJustInTimeBinding.setAccessible(true);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	private Class<T> clazz;

	public JustInTimeProvider(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Inject
	Injector injector;

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		try {
			Key<T> key = Key.get(clazz);
			final Dependency<T> dependency = Dependency.get(key);
			final Errors errors = new Errors(this);
			BindingImpl<T> binding = (BindingImpl<T>) getJustInTimeBinding
					.invoke(injector, key, errors);
			final InternalFactory<? extends T> factory = binding
					.getInternalFactory();
			InjectorImpl injectorImpl = (InjectorImpl) injector;

			T t = injectorImpl.callInContext(new ContextualCallable<T>() {
				public T call(InternalContext context) throws ErrorsException {
					context.setDependency(dependency);
					try {
						return factory.get(errors, context, dependency);
					} finally {
						context.setDependency(null);
					}
				}
			});
			return t;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
