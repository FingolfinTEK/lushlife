package lushfile.core.guice;

import java.util.Map;

import lushfile.core.context.LushContext;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

public class HiddenScope implements Scope {

	@Override
	public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
		return new Provider<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T get() {
				Map<String, Object> map = LushContext.getHiddenScope();
				Object obj = map.get(key.toString());
				if (obj == null) {
					obj = unscoped.get();
					map.put(key.toString(), obj);
				}
				return (T) obj;
			}

		};
	}
}
