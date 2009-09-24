package org.lushlife.negroni.delegate.impl;

import java.lang.reflect.Method;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.conversions.Conversions;
import org.lushlife.negroni.delegate.Precedence;
import org.lushlife.negroni.util.Reflections;

@Precedence(100)
public class MixinMethodDelegate extends AbstractDelegateMethod {
	final Class id;

	public MixinMethodDelegate(Method method, Class id) {
		super(method);
		this.id = id;
	}

	public boolean isAccept(Class<?> owner, Method m) {
		if (!getDelegateMethod().getName().equals(m.getName())) {
			return false;
		}
		Class<?>[] classes = getDelegateMethod().getParameterTypes();
		if (classes.length == 0) {
			return false;
		}
		if (!Conversions.isConvert(owner, classes[0])) {
			return false;
		}

		return Reflections.isSimpleTypeAccept(m, getDelegateMethod(), 1);
	}

	public Object invoke(Container context, Object owner, Method method,
			Object[] args) {
		Object[] tmp = Reflections.toSimpleTypeArgs(new Object[] { owner },
				args);

		return Reflections.invoke(context.getInstance(id), getDelegateMethod(),
				tmp);
	}
}
