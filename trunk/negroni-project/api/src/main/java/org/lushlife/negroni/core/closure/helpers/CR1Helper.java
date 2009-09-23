package org.lushlife.negroni.core.closure.helpers;

import java.lang.reflect.Method;

import org.lushlife.negroni.core.closure.CR1;
import org.lushlife.negroni.core.closure.Closure;
import org.lushlife.negroni.core.closure.ClosureHelper;
import org.lushlife.negroni.core.impl.reflection.Reflections;



public class CR1Helper extends ClosureHelper {

	public CR1Helper() {
		super(CR1.class);
	}

	@Override
	public boolean isAccept(Method m) {
		if (m.getReturnType() == void.class) {
			return false;
		}
		if (m.getParameterTypes().length != 1) {
			return false;
		}
		return true;
	}

	@Override
	public Closure<?> toDelegator(final Object mixin, final Method m) {
		return new CR1<Object, Object>() {
			public Object call(Object p1) {
				return Reflections.invoke(mixin, m, new Object[] { p1 });
			}
		};
	}

}
