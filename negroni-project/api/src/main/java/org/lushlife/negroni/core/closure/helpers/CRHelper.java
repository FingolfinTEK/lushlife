package org.lushlife.negroni.core.closure.helpers;

import java.lang.reflect.Method;

import org.lushlife.negroni.core.closure.CR;
import org.lushlife.negroni.core.closure.Closure;
import org.lushlife.negroni.core.closure.ClosureHelper;
import org.lushlife.negroni.core.impl.reflection.Reflections;



public class CRHelper extends ClosureHelper {

	public CRHelper() {
		super(CR.class);
	}

	@Override
	public boolean isAccept(Method m) {
		if (m.getReturnType() == void.class) {
			return false;
		}
		if (m.getParameterTypes().length != 0) {
			return false;
		}
		return true;
	}

	@Override
	public Closure<?> toDelegator(final Object mixin, final Method m) {
		return new CR<Object>() {
			public Object call() {
				return Reflections.invoke(mixin, m, new Object[] {});
			}
		};
	}

}
