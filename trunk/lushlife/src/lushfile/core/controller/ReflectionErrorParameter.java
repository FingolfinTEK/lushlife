package lushfile.core.controller;

import java.lang.reflect.Method;

public class ReflectionErrorParameter {
	private String packageName;
	private Method method;
	private Object instance;
	private Exception exception;

	public Method getMethod() {
		return method;
	}

	public Exception getException() {
		return exception;
	}

	public ReflectionErrorParameter init(String packageName, Method method,
			Object instance, Exception e) {
		this.packageName = packageName;
		this.exception = e;
		this.instance = instance;
		this.method = method;
		return this;
	}

	public Object getInstance() {
		return instance;
	}

	public String getPackageName() {
		return packageName;
	}

}
