package com.google.inject;

public class LatteGuiceUtil {

	static public Injector getInjector(Binder binder) {
		return ((BinderImpl) binder).injector;
	}

	static public <T> void bindFactory(Binder binder, Key<T> key,
			InternalFactoryPublic<T> factory) {
		((BinderImpl) binder).bind(key).to(factory);
	}
}
