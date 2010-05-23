package org.lushlife.guicexml.aop;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

public class Ejb3InterceptorImpl implements Ejb3Interceptor {
	private Object owner;
	private Method method;

	public Ejb3InterceptorImpl(Object owner, Method method) {
		this.owner = owner;
		this.method = method;
	}

	@Override
	public Object aroundInvoke(InvocationContext invocationContext)
			throws Exception {
		return method.invoke(owner, invocationContext);
	}

}
