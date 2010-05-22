package com.google.inject;

import java.lang.reflect.Method;

import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.spi.Dependency;

public class JustInTimeProvider<T> implements Provider<T> {
	static private Method getJustInTimeBinding;
	static {
		try {
			getJustInTimeBinding = InjectorImpl.class.getDeclaredMethod(
					"getJustInTimeBinding", Key.class, Errors.class);
			getJustInTimeBinding.setAccessible(true);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	private Class<T> clazz;

	public JustInTimeProvider(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Inject
	Injector injector;

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		try {
			Key<T> key = Key.get(clazz);
			final Dependency<T> dependency = Dependency.get(key);
			final Errors errors = new Errors(this);
			BindingImpl<T> binding = (BindingImpl<T>) getJustInTimeBinding
					.invoke(injector, key, errors);
			final InternalFactory<? extends T> factory = binding
					.getInternalFactory();
			T t = ((InjectorImpl) injector)
					.callInContext(new ContextualCallable<T>() {
						public T call(InternalContext context)
								throws ErrorsException {
							context.setDependency(dependency);
							try {
								return factory.get(errors, context, dependency);
							} finally {
								context.setDependency(null);
							}
						}
					});
			return t;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
