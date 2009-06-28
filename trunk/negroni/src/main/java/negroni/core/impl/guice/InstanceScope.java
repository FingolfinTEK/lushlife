package negroni.core.impl.guice;

import java.util.Map;

import negroni.core.impl.scope.NegroniContext;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

public class InstanceScope implements Scope {

	public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
		return new Provider<T>() {
			public T get() {
				Map<Object, Object> scoped = NegroniContext.instanceScope.get();
				if (scoped == null) {
					return unscoped.get();
				}
				if (scoped.containsKey(key)) {
					return (T) scoped.get(key);
				}
				T obj = unscoped.get();
				scoped.put(key, obj);
				return obj;
			}

		};
	}

}
