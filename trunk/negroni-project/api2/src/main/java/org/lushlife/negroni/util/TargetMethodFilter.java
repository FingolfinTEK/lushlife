package org.lushlife.negroni.util;

import java.lang.reflect.Method;
import java.util.Set;

import javassist.util.proxy.MethodFilter;

public class TargetMethodFilter implements MethodFilter {
	private Set<Method> target;

	public TargetMethodFilter(Set<Method> target) {
		this.target = target;
	}

	public boolean isHandled(Method m) {
		return target.contains(m);
	}
}