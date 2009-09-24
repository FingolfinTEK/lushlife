package org.lushlife.negroni.delegate.impl;

import java.lang.reflect.Method;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.delegate.Precedence;
import org.lushlife.negroni.util.Reflections;

@Precedence(400)
public class MethodMissingVarArgsMethodDelegate extends
		AbstractVarArgsDelegatemMethod {

	public MethodMissingVarArgsMethodDelegate(Method method) {
		super(method);
	}

	public boolean isAccept(Class<?> owner, Method m) {
		return Reflections.isVarArgsAccept(m, getDelegateMethod(), 1);
	}

	public Object invoke(Container context, Object owner, Method method,
			Object[] args) {
		if (method.isVarArgs()) {
			args = Reflections.varargsFlatten(args);
		}
		Object[] tmp = Reflections.toVarargsArgs(new Object[] { method }, args,
				getVarArgsPosition(), getVarArgsType());
		return Reflections.invoke(owner, getDelegateMethod(), tmp);
	}

}
