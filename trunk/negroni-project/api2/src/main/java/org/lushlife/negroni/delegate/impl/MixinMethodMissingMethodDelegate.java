package org.lushlife.negroni.delegate.impl;

import java.lang.reflect.Method;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.conversions.Conversions;
import org.lushlife.negroni.delegate.Precedence;
import org.lushlife.negroni.util.Reflections;

@Precedence(500)
public class MixinMethodMissingMethodDelegate extends AbstractDelegateMethod {

	Class id;

	public MixinMethodMissingMethodDelegate(Method method, Class id) {
		super(method);
		this.id = id;
	}

	public boolean isAccept(Class<?> owner, Method m) {
		if (!Conversions.isConvert(owner, getDelegateMethod()
				.getParameterTypes()[0])) {
			return false;
		}
		return Reflections.isSimpleTypeAccept(m, getDelegateMethod(), 2);
	}

	public Object invoke(Container context, Object owner, Method method,
			Object[] args) throws Exception {
		Object[] tmp = Reflections.toSimpleTypeArgs(new Object[] { owner,
				method }, args);
		return Reflections.invoke(context.getInstance(id), getDelegateMethod(),
				tmp);
	}
}
