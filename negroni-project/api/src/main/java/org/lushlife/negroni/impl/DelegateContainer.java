package org.lushlife.negroni.impl;

import org.lushlife.negroni.Container;

public abstract class DelegateContainer implements Container {

	protected abstract Container delegate();

	public <T> T getInstance(Class<T> clazz) {
		return delegate().getInstance(clazz);
	}

}
