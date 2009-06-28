package negroni.core.closure.helpers;

import java.lang.reflect.Method;

import negroni.core.closure.CV;
import negroni.core.closure.CV1;
import negroni.core.closure.Closure;
import negroni.core.closure.ClosureHelper;
import negroni.core.impl.reflection.Reflections;


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
