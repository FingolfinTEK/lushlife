package org.lushlife.guicexml.aop;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public interface Ejb3Interceptor {

	@AroundInvoke
	public abstract Object aroundInvoke(InvocationContext invocationContext)
			throws Exception;

}
