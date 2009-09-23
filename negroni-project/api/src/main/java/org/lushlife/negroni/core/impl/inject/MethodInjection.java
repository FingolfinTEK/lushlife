package org.lushlife.negroni.core.impl.inject;

import java.lang.reflect.Method;

import org.lushlife.negroni.core.impl.reflection.Reflections;



public class MethodInjection<T> implements Injection<T> {
	Method setter;
	String name;

	public String getName() {
		return name;
	}

	public MethodInjection(String name, Method setter) {
		this.setter = setter;
		this.name = name;
	}

	public void setValue(Object owner, T value) {
		Reflections.invoke(owner, setter, new Object[] { value });
	}

	public String toString() {
		return setter.toString();
	}

}
