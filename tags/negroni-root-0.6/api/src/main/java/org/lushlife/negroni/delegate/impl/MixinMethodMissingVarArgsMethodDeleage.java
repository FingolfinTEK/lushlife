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
import org.lushlife.negroni.conversions.Conversions;
import org.lushlife.negroni.delegate.DelegateMethodPrecedence;
import org.lushlife.negroni.util.Reflections;

/**
 * @author Takeshi Kondo
 */
@DelegateMethodPrecedence(600)
public class MixinMethodMissingVarArgsMethodDeleage extends
		AbstractVarArgsDelegatemMethod {

	private Class mixinClass;
	private Class<?> mixinInstanceType;
	private int methodMissing;

	public MixinMethodMissingVarArgsMethodDeleage(Class<?> mixinInstanceType,
			int methodMissing, Method method, Class mixinClass) {
		super(method);
		this.mixinInstanceType = mixinInstanceType;
		this.methodMissing = methodMissing;
		this.mixinClass = mixinClass;
	}

	public boolean isAccept(Class<?> owner, Method m) {
		if (!Conversions.isConvert(owner, mixinInstanceType)) {
			return false;
		}
		return Reflections.isVarArgsAccept(m, getDelegateMethod(), 1);
	}

	public Object invoke(Map<String, Object> contextMap, Container context,
			Object owner, Method method, Object[] args) throws Exception {
		if (method.isVarArgs()) {
			args = Reflections.varargsFlatten(args);
		}
		Object[] tmp = Reflections.toVarargsArgs(new Object[] { method }, args,
				getVarArgsPosition(), getVarArgsType());
		Object instance = getMixinInstance(context, owner, mixinClass,
				contextMap);
		return Reflections.invoke(instance, getDelegateMethod(), tmp);
	}
}
