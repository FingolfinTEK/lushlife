package org.lushlife.negroni.core.impl.guice;

import org.lushlife.negroni.Identifier;
import org.lushlife.negroni.core.impl.ConfiguratorBase;
import org.lushlife.negroni.core.impl.EnhancerBase;

import com.google.inject.Injector;

class GuiceEnhancer extends EnhancerBase {

	final Injector injector;

	public GuiceEnhancer(Injector injector, ConfiguratorBase configurator) {
		super(configurator);
		this.injector = injector;
	}

	public <T> T getInstance(Identifier<T> id) {
		return injector.getInstance(((GuiceIdentifer<T>) id).getKey());
	}

	public <T> boolean contains(Identifier<T> id) {
		if (id instanceof GuiceIdentifer) {
			return true;
		}
		return false;
	}

	public Injector getInjector() {
		return injector;
	}

	public <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

}
