package org.lushlife.negroni.core.impl.inject;

import org.lushlife.negroni.Enhancer;

public class InjectionUnit<T> {
	final Injection<T> injection;
	final InjectedValue<T> injectedValue;

	public InjectionUnit(Injection<T> injection, InjectedValue<T> injectedValue) {
		this.injection = injection;
		this.injectedValue = injectedValue;
	}

	public void inject(Object owner, Enhancer container) {
		injection.setValue(owner, injectedValue.getValue(container));
	}

	public String toString() {
		return injection + "\t" + injectedValue;
	}

}
