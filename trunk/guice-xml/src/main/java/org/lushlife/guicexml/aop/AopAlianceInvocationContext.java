package org.lushlife.guicexml.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.interceptor.InvocationContext;

import org.aopalliance.intercept.MethodInvocation;

public class AopAlianceInvocationContext implements InvocationContext {
	static private ThreadLocal<Map<String, Object>> contextData = new ThreadLocal<Map<String, Object>>();
	private MethodInvocation invocation;

	public AopAlianceInvocationContext(MethodInvocation invocation) {
		this.invocation = invocation;
	}

	@Override
	public Map<String, Object> getContextData() {
		return contextData.get();
	}

	@Override
	public Method getMethod() {
		return invocation.getMethod();
	}

	@Override
	public Object[] getParameters() {
		return invocation.getArguments();
	}

	@Override
	public Object getTarget() {
		return invocation.getThis();
	}

	@Override
	public Object proceed() throws Exception {
		Map<String, Object> map = contextData.get();
		boolean createMap = false;
		if (map == null) {
			createMap = true;
			contextData.set(new HashMap<String, Object>());
		}
		try {
			return invocation.proceed();
		} catch (Throwable e) {
			if (e instanceof Exception) {
				throw (Exception) e;
			}
			throw new InvocationTargetException(e);
		} finally {
			if (createMap) {
				contextData.remove();
			}
		}
	}

	@Override
	public void setParameters(Object[] parameters) {
		System.arraycopy(parameters, 0, getParameters(), 0, parameters.length);
	}

}
