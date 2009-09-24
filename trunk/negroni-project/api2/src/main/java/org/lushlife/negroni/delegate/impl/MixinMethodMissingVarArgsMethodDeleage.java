package org.lushlife.negroni.delegate.impl;

import java.lang.reflect.Method;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.conversions.Conversions;
import org.lushlife.negroni.delegate.Precedence;
import org.lushlife.negroni.util.Reflections;

@Precedence(600)
public class MixinMethodMissingVarArgsMethodDeleage extends
		AbstractVarArgsDelegatemMethod {

	private Class id;

	public MixinMethodMissingVarArgsMethodDeleage(Method method, Class id) {
		super(method);
		this.id = id;
	}

	public boolean isAccept(Class<?> owner, Method m) {
		if (!Conversions.isConvert(owner, getDelegateMethod()
				.getParameterTypes()[0])) {
			return false;
		}
		return Reflections.isVarArgsAccept(m, getDelegateMethod(), 2);
	}

	public Object invoke(Container context, Object owner, Method method,
			Object[] args) {
		if (method.isVarArgs()) {
			args = Reflections.varargsFlatten(args);
		}
		Object[] tmp = Reflections.toVarargsArgs(
				new Object[] { owner, method }, args, getVarArgsPosition(),
				getVarArgsType());
		return Reflections.invoke(context.getInstance(id), getDelegateMethod(),
				tmp);
	}
}
