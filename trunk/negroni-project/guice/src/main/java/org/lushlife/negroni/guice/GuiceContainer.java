package org.lushlife.negroni.guice;

import org.lushlife.negroni.Container;

import com.google.inject.Injector;

public class GuiceContainer implements Container {
	private Injector injector;

	public GuiceContainer(Injector injector) {
		this.injector = injector;
	}

	public <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

}
