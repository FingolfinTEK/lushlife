package org.lushlife.negroni.core.impl.delegate;

import java.lang.reflect.Method;

import org.lushlife.negroni.core.impl.reflection.Reflections;




abstract class AbstractDelegateMethod implements DelegateMethod {

	final private Method method;

	public AbstractDelegateMethod(Method method) {
		this.method = method;
		method.setAccessible(true);
	}

	public int compareTo(DelegateMethod o) {
		int classCom = Reflections.classCompare(this, o);
		if (classCom != 0) {
			return classCom;
		}
		return Reflections.methodCompare(this.getDelegateMethod(), o
				.getDelegateMethod());

	}

	public Method getDelegateMethod() {
		return method;
	}

	public String toString() {
		return this.getClass().getSimpleName() + ":" + method;
	}

}
