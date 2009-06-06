package lushfile.core.controller;

import java.lang.reflect.Method;

public class ActionParameter {
	private String packageName;
	private Method method;
	private Object instance;

	public String getPackageName() {
		return packageName;
	}

	private Object fromAction;

	public ActionParameter init(String packageName, Method method,
			Object instance, Object fromAction) {
		this.packageName = packageName;
		this.method = method;
		this.instance = instance;
		this.fromAction = fromAction;
		return this;
	}

	public Method getMethod() {
		return method;
	}

	public Object getInstance() {
		return instance;
	}

	public Object getFromAction() {
		return fromAction;
	}

}
