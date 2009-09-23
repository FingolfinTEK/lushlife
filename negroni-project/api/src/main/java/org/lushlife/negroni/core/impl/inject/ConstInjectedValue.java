package org.lushlife.negroni.core.impl.inject;

import org.lushlife.negroni.Enhancer;

public class ConstInjectedValue<T> implements InjectedValue<T> {
	final T value;

	public ConstInjectedValue(T value) {
		this.value = value;
	}

	public T getValue(Enhancer container) {
		return value;
	}

	public String toString() {
		return "const(type=" + value.getClass() + ",value=" + value + ")";
	}
}
