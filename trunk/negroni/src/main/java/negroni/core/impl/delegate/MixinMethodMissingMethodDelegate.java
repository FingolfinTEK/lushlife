package negroni.core.impl.delegate;

import java.lang.reflect.Method;

import negroni.Enhancer;
import negroni.Identifier;
import negroni.core.impl.conversions.Conversions;
import negroni.core.impl.delegate.annotation.Precedence;
import negroni.core.impl.reflection.Reflections;
import negroni.core.impl.scope.InstanceContext;


@Precedence(500)
class MixinMethodMissingMethodDelegate extends AbstractDelegateMethod {

	Identifier<?> id;

	public MixinMethodMissingMethodDelegate(Method method, Identifier<?> id) {
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

	public Object invoke(Enhancer context, Object owner, Method method,
			Object[] args, InstanceContext scope) {
		Object[] tmp = Reflections.toSimpleTypeArgs(new Object[] { owner,
				method }, args);
		return Reflections.invoke(id.getScope().scoped(id, context, scope),
				getDelegateMethod(), tmp);
	}
}
