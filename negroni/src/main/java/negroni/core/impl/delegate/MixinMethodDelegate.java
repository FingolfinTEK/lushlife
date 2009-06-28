package negroni.core.impl.delegate;

import java.lang.reflect.Method;

import negroni.Enhancer;
import negroni.Identifier;
import negroni.core.impl.conversions.Conversions;
import negroni.core.impl.delegate.annotation.Precedence;
import negroni.core.impl.reflection.Reflections;
import negroni.core.impl.scope.InstanceContext;


@Precedence(100)
class MixinMethodDelegate extends AbstractDelegateMethod {
	final Identifier<?> id;

	public MixinMethodDelegate(Method method, Identifier<?> id) {
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

	public Object invoke(Enhancer context, Object owner, Method method,
			Object[] args, InstanceContext scope) {
		Object[] tmp = Reflections.toSimpleTypeArgs(new Object[] { owner },
				args);

		return Reflections.invoke(id.getScope().scoped(id, context, scope),
				getDelegateMethod(), tmp);
	}
}
