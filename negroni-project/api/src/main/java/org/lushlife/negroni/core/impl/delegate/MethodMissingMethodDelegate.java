package org.lushlife.negroni.core.impl.delegate;

import java.lang.reflect.Method;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.core.impl.delegate.annotation.Precedence;
import org.lushlife.negroni.core.impl.reflection.Reflections;
import org.lushlife.negroni.core.impl.scope.InstanceContext;



@Precedence(300)
class MethodMissingMethodDelegate extends AbstractDelegateMethod {

	public MethodMissingMethodDelegate(Method method) {
		super(method);
	}

	public boolean isAccept(Class<?> owner, Method m) {
		return Reflections.isSimpleTypeAccept(m, getDelegateMethod(), 1);
	}

	public Object invoke(Enhancer context, Object owner, Method method,
			Object[] args, InstanceContext scope) {
		Object[] tmp = Reflections.toSimpleTypeArgs(new Object[] { method },
				args);
		return Reflections.invoke(owner, getDelegateMethod(), tmp);
	}

}
