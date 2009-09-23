package negroni.core.impl.delegate;

import java.lang.reflect.Method;

import negroni.Enhancer;
import negroni.core.impl.delegate.annotation.Precedence;
import negroni.core.impl.reflection.Reflections;
import negroni.core.impl.scope.InstanceContext;


@Precedence(400)
class MethodMissingVarArgsMethodDelegate extends AbstractVarArgsDelegatemMethod {

	public MethodMissingVarArgsMethodDelegate(Method method) {
		super(method);
	}

	public boolean isAccept(Class<?> owner, Method m) {
		return Reflections.isVarArgsAccept(m, getDelegateMethod(), 1);
	}

	public Object invoke(Enhancer context, Object owner, Method method,
			Object[] args, InstanceContext scope) {
		if (method.isVarArgs()) {
			args = Reflections.varargsFlatten(args);
		}
		Object[] tmp = Reflections.toVarargsArgs(new Object[] { method }, args,
				getVarArgsPosition(), getVarArgsType());
		return Reflections.invoke(owner, getDelegateMethod(), tmp);
	}

}
