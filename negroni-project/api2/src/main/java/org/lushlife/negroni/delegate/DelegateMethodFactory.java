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
