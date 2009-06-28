package com.google.inject;

public abstract class InternalFactoryPublic<T> implements InternalFactory<T> {

	abstract public T get(InternalContextPublic context);

	public T get(InternalContext context) {
		return get(new InternalContextPublic(context.getInjectorImpl()));
	}

}
