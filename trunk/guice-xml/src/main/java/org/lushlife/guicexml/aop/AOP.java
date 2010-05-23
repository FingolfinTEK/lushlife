package org.lushlife.guicexml.aop;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;

public class AOP {

	static public Ejb3Interceptor toInterceptor(Object obj) {
		if (obj instanceof Ejb3Interceptor) {
			return (Ejb3Interceptor) obj;
		}
		for (Method method : obj.getClass().getMethods()) {
			if (method.isAnnotationPresent(AroundInvoke.class)) {
				return new Ejb3InterceptorImpl(obj, method);
			}
		}

		throw new IllegalArgumentException("isn't ejb3 interceptor "
				+ obj.getClass());
	}
}
