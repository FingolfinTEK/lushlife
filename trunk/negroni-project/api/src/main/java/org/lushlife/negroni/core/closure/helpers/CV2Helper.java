package org.lushlife.negroni.core.closure.helpers;

import java.lang.reflect.Method;

import org.lushlife.negroni.core.closure.CV2;
import org.lushlife.negroni.core.closure.Closure;
import org.lushlife.negroni.core.closure.ClosureHelper;
import org.lushlife.negroni.core.impl.reflection.Reflections;



public class CV2Helper extends ClosureHelper {

	public CV2Helper() {
		super(CV2.class);
	}

	@Override
	public boolean isAccept(Method m) {
		if (m.getReturnType() != void.class) {
			return false;
		}
		if (m.getParameterTypes().length != 2) {
			return false;
		}
		return true;
	}

	@Override
	public Closure<?> toDelegator(final Object mixin, final Method m) {
		return new CV2<Object, Object>() {
			public void call(Object p1, Object p2) {
				Reflections.invoke(mixin, m, new Object[] { p1, p2 });
			}
		};
	}

}
