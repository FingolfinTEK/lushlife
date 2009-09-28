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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.conversions.Conversions;
import org.lushlife.negroni.delegate.DelegateMethodPrecedence;
import org.lushlife.negroni.util.Reflections;

/**
 * @author Takeshi Kondo
 */
@DelegateMethodPrecedence(100)
public class MixinMethodDelegate extends AbstractDelegateMethod {
	final private Class mixinClass;
	private Field mixinField;

	public MixinMethodDelegate(Field mixInstanceType, Method method,
			Class mixinClass) {
		super(method);
		this.mixinClass = mixinClass;
		this.mixinField = mixInstanceType;
	}

	public boolean isAccept(Class<?> owner, Method m) {
		if (!getDelegateMethod().getName().equals(m.getName())) {
			return false;
		}
		if (mixinField!=  null &&!Conversions.isConvert(owner, mixinField.getType())) {
			return false;
		}

		return Reflections.isSimpleTypeAccept(m, getDelegateMethod(), 0);
	}

	public Object invoke(Map<String, Object> contextMap, Container context,
			Object owner, Method method, Object[] args) throws Exception {
		Object[] tmp = Reflections.toSimpleTypeArgs(new Object[] {}, args);
		Object instance = getMixinInstance(mixinField,context, owner, mixinClass,
				contextMap);
		return Reflections.invoke(instance, getDelegateMethod(), tmp);
	}

}
