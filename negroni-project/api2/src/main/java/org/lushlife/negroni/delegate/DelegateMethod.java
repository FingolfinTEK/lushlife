package org.lushlife.negroni.delegate;

import java.lang.reflect.Method;

import org.lushlife.negroni.Container;

public interface DelegateMethod extends Comparable<DelegateMethod> {

	boolean isAccept(Class<?> owner, Method m);

	Method getDelegateMethod();

	Object invoke(Container container, Object owner, Method method,
			Object[] args);

}
