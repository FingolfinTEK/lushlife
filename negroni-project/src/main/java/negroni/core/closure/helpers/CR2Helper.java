package negroni.core.closure.helpers;

import java.lang.reflect.Method;

import negroni.core.closure.CR2;
import negroni.core.closure.Closure;
import negroni.core.closure.ClosureHelper;
import negroni.core.impl.reflection.Reflections;


public class CR2Helper extends ClosureHelper {

	public CR2Helper() {
		super(CR2.class);
	}

	@Override
	public boolean isAccept(Method m) {
		if (m.getReturnType() == void.class) {
			return false;
		}
		if (m.getParameterTypes().length != 2) {
			return false;
		}
		return true;
	}

	@Override
	public Closure<?> toDelegator(final Object mixin, final Method m) {
		return new CR2<Object, Object, Object>() {
			public Object call(Object p1, Object p2) {
				return Reflections.invoke(mixin, m, new Object[] { p1, p2 });
			}
		};
	}

}
