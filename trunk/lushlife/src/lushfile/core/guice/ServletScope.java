package lushfile.core.guice;

import javax.servlet.ServletContext;

import lushfile.core.context.LushContext;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

public class ServletScope implements Scope {

	@Override
	public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
		return new Provider<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T get() {
				ServletContext context = LushContext.getServletContext();
				Object obj = context.getAttribute(key.toString());
				if (obj == null) {
					synchronized (context) {
						obj = context.getAttribute(key.toString());
						if (obj == null) {
							obj = unscoped.get();
							context.setAttribute(key.toString(), obj);
						}
					}
				}
				return (T) obj;
			}

		};
	}
}
