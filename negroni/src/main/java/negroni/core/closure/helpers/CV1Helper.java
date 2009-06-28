package negroni.core.closure.helpers;

import java.lang.reflect.Method;

import negroni.core.closure.CV1;
import negroni.core.closure.Closure;
import negroni.core.closure.ClosureHelper;
import negroni.core.impl.reflection.Reflections;


public class CV1Helper extends ClosureHelper {

	public CV1Helper() {
		super(CV1.class);
	}

	@Override
	public boolean isAccept(Method m) {
		if (m.getReturnType() != void.class) {
			return false;
		}
		if (m.getParameterTypes().length != 1) {
			return false;
		}
		return true;
	}

	@Override
	public Closure<?> toDelegator(final Object mixin, final Method m) {
		return new CV1<Object>() {
			public void call(Object p1) {
				Reflections.invoke(mixin, m, new Object[] { p1 });
			}
		};
	}

}
