package org.lushlife.negroni.core.impl.delegate;

import java.lang.reflect.Method;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Identifier;
import org.lushlife.negroni.core.impl.conversions.Conversions;
import org.lushlife.negroni.core.impl.delegate.annotation.Precedence;
import org.lushlife.negroni.core.impl.reflection.Reflections;
import org.lushlife.negroni.core.impl.scope.InstanceContext;



@Precedence(600)
class MixinMethodMissingVarArgsMethodDeleage extends
		AbstractVarArgsDelegatemMethod {

	Identifier<?> id;

	public MixinMethodMissingVarArgsMethodDeleage(Method method,
			Identifier<?> id) {
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

	public Object invoke(Enhancer context, Object owner, Method method,
			Object[] args, InstanceContext scope) {
		if (method.isVarArgs()) {
			args = Reflections.varargsFlatten(args);
		}
		Object[] tmp = Reflections.toVarargsArgs(
				new Object[] { owner, method }, args, getVarArgsPosition(),
				getVarArgsType());
		return Reflections.invoke(id.getScope().scoped(id, context, scope),
				getDelegateMethod(), tmp);
	}

}
