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
package org.lushlife.negroni.delegate;

import java.lang.reflect.Method;

import org.lushlife.negroni.MethodMissing;
import org.lushlife.negroni.MixinMethod;
import org.lushlife.negroni.delegate.impl.MethodMissingMethodDelegate;
import org.lushlife.negroni.delegate.impl.MethodMissingVarArgsMethodDelegate;
import org.lushlife.negroni.delegate.impl.MixinMethodDelegate;
import org.lushlife.negroni.delegate.impl.MixinMethodMissingMethodDelegate;
import org.lushlife.negroni.delegate.impl.MixinMethodMissingVarArgsMethodDeleage;
import org.lushlife.negroni.delegate.impl.MixinVarArgsMethodDelegate;

/**
 * @author Takeshi Kondo
 */
public class DelegateMethodFactory {

	static public DelegateMethod toDelegateMethod(Method m, Class<?> clazz) {
		boolean isMethodMissing = m.isAnnotationPresent(MethodMissing.class);
		boolean isMixin = m.isAnnotationPresent(MixinMethod.class);
		boolean isVarArgs = m.isVarArgs();

		if (isMethodMissing && isMixin) {
			if (isVarArgs) {
				return new MixinMethodMissingVarArgsMethodDeleage(m, clazz);
			} else {
				return new MixinMethodMissingMethodDelegate(m, clazz);
			}
		}
		if (isMethodMissing) {
			if (isVarArgs) {
				return new MethodMissingVarArgsMethodDelegate(m);
			} else {
				return new MethodMissingMethodDelegate(m);
			}
		}
		if (isMixin) {
			if (isVarArgs) {
				return new MixinVarArgsMethodDelegate(m, clazz);
			} else {
				return new MixinMethodDelegate(m, clazz);
			}
		}
		return null;
	}

}
