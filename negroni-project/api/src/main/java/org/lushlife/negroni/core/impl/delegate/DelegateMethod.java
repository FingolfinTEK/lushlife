package org.lushlife.negroni.core.impl.delegate;

import java.lang.reflect.Method;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.core.impl.scope.InstanceContext;



public interface DelegateMethod extends Comparable<DelegateMethod> {

	boolean isAccept(Class<?> owner, Method m);

	Method getDelegateMethod();

	Object invoke(Enhancer container, Object owner, Method method,
			Object[] args, InstanceContext perinstanceScope);

}
