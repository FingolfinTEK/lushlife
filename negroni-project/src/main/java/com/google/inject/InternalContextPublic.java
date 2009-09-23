package com.google.inject;

public class InternalContextPublic extends InternalContext {

	InternalContextPublic(InjectorImpl injector) {
		super(injector);
	}

	@Override
	public <T> ExternalContext<T> getExternalContext() {
		return super.getExternalContext();
	}

	@Override
	public InjectorImpl getInjectorImpl() {
		return super.getInjectorImpl();
	}

}
