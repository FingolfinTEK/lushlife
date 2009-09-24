package org.lushlife.negroni.util;

import java.lang.reflect.Method;
import java.util.Map;

import javassist.util.proxy.MethodHandler;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.delegate.DelegateMethod;

public class DelegateMethodHandler implements MethodHandler {

	Container container;
	Map<Method, DelegateMethod> mapping;

	public DelegateMethodHandler(Container container,
			Map<Method, DelegateMethod> mapping) {
		this.container = container;
		this.mapping = mapping;
	}

	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		return mapping.get(thisMethod)
				.invoke(container, self, thisMethod, args);
	}
}