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
package org.lushlife.negroni.delegate.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.MixinInterface;
import org.lushlife.negroni.delegate.DelegateMethod;
import org.lushlife.negroni.util.Reflections;

/**
 * @author Takeshi Kondo
 */
public abstract class AbstractDelegateMethod implements DelegateMethod {

	final private Method method;

	public AbstractDelegateMethod(Method method) {
		this.method = method;
		method.setAccessible(true);
	}

	public int compareTo(DelegateMethod o) {
		int classCom = Reflections.classCompare(this, o);
		if (classCom != 0) {
			return classCom;
		}
		return Reflections.methodCompare(this.getDelegateMethod(), o
				.getDelegateMethod());

	}

	public Method getDelegateMethod() {
		return method;
	}

	public String toString() {
		return this.getClass().getSimpleName() + ":" + method;
	}

	protected Object getMixinInstance(Container context, Object owner,
			Class mixinClass, Map<String, Object> contextMap) {

		if (context.isManagementScope(mixinClass)) {
			Object instance = createAndInject(context, mixinClass, owner);
			return instance;
		} else {
			Object instance = contextMap.get(mixinClass.getName());
			if (instance != null) {
				return instance;
			}
			instance = createAndInject(context, mixinClass, owner);
			contextMap.put(mixinClass.getName(), instance);
			return instance;
		}
	}

	private Object createAndInject(Container context, Class mixinClass,
			Object owner) {
		Object instance;
		instance = context.getInstance(mixinClass);
		if (instance instanceof MixinInterface) {
			((MixinInterface) instance).setMixinInstance(owner);
		}
		return instance;
	}

}
