package org.lushlife.negroni.core.closure.helpers;

import java.lang.reflect.Method;

import org.lushlife.negroni.core.closure.CV;
import org.lushlife.negroni.core.closure.CV1;
import org.lushlife.negroni.core.closure.Closure;
import org.lushlife.negroni.core.closure.ClosureHelper;
import org.lushlife.negroni.core.impl.reflection.Reflections;



public class CVHelper extends ClosureHelper {

	public CVHelper() {
		super(CV1.class);
	}

	@Override
	public boolean isAccept(Method m) {
		if (m.getReturnType() != void.class) {
			return false;
		}
		if (m.getParameterTypes().length != 0) {
			return false;
		}
		return true;
	}

	@Override
	public Closure<?> toDelegator(final Object mixin, final Method m) {
		return new CV() {
			public void call() {
				Reflections.invoke(mixin, m, new Object[] {});
			}
		};
	}

}
