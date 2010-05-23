package org.lushlife.guicexml.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AopAlianceEjb3Inverceptor implements MethodInterceptor {
	private Ejb3Interceptor interceptor;

	public AopAlianceEjb3Inverceptor(Ejb3Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return interceptor.aroundInvoke(new AopAlianceInvocationContext(
				invocation));
	}

}
