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

import org.lushlife.negroni.LogMsgNGLN;
import org.lushlife.negroni.MissingMethod;
import org.lushlife.negroni.MixinMethod;
import org.lushlife.negroni.delegate.impl.MethodMissingMethodDelegate;
import org.lushlife.negroni.delegate.impl.MethodMissingVarArgsMethodDelegate;
import org.lushlife.negroni.delegate.impl.MixinMethodDelegate;
import org.lushlife.negroni.delegate.impl.MixinMethodMissingMethodDelegate;
import org.lushlife.negroni.delegate.impl.MixinMethodMissingVarArgsMethodDeleage;
import org.lushlife.negroni.delegate.impl.MixinVarArgsMethodDelegate;
import org.lushlife.negroni.util.Reflections;
import org.lushlife.stla.Logging;

/**
 * @author Takeshi Kondo
 */
public class DelegateMethodFactory {

	static public DelegateMethod toDelegateMethod(Method m, Class<?> mixinClass) {
		int methodMissingPosition = Reflections.searchParameterAnnotation(m,
				MissingMethod.class);
		boolean isMixin = m.isAnnotationPresent(MixinMethod.class);
		boolean isVarArgs = m.isVarArgs();
		Class<?> mixinInstanceType = Reflections.getActualMixinType(mixinClass);
		if (methodMissingPosition >= 0) {
			Class<?> methodMissing = m.getParameterTypes()[methodMissingPosition];
			if (!methodMissing.equals(Method.class)) {
				throw new IllegalArgumentException(Logging.getMessage(
						LogMsgNGLN.NGLN00005, m, methodMissingPosition));
			}
		}
		if (methodMissingPosition >= 0 && isMixin) {
			if (isVarArgs) {
				return new MixinMethodMissingVarArgsMethodDeleage(
						mixinInstanceType, methodMissingPosition, m, mixinClass);
			} else {
				return new MixinMethodMissingMethodDelegate(mixinInstanceType,
						methodMissingPosition, m, mixinClass);
			}
		}
		if (methodMissingPosition >= 0) {
			if (isVarArgs) {
				return new MethodMissingVarArgsMethodDelegate(
						methodMissingPosition, m);
			} else {
				return new MethodMissingMethodDelegate(methodMissingPosition, m);
			}
		}
		if (isMixin) {
			if (isVarArgs) {
				return new MixinVarArgsMethodDelegate(mixinInstanceType, m,
						mixinClass);
			} else {
				return new MixinMethodDelegate(mixinInstanceType, m, mixinClass);
			}
		}
		return null;
	}

}
