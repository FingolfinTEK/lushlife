package org.lushlife.negroni.delegate.impl;

import java.lang.reflect.Method;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.delegate.Precedence;
import org.lushlife.negroni.util.Reflections;

@Precedence(300)
public class MethodMissingMethodDelegate extends AbstractDelegateMethod {

	public MethodMissingMethodDelegate(Method method) {
		super(method);
	}

	public boolean isAccept(Class<?> owner, Method m) {
		return Reflections.isSimpleTypeAccept(m, getDelegateMethod(), 1);
	}

	public Object invoke(Container context, Object owner, Method method,
			Object[] args) throws Exception {
		Object[] tmp = Reflections.toSimpleTypeArgs(new Object[] { method },
				args);
		return Reflections.invoke(owner, getDelegateMethod(), tmp);
	}

}
