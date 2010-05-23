package org.lushlife.guicexml.spi;

import java.lang.annotation.Annotation;
import java.util.Map;

public abstract class ScopeBinding {
	static public interface ToName {
		void toName(String name);
	}

	private Map<String, Class<? extends Annotation>> mapping;

	public void configure(Map<String, Class<? extends Annotation>> mapping) {
		this.mapping = mapping;
		configuire();
	}

	protected ToName bindScope(final Class<? extends Annotation> scopeAnnotation) {
		return new ToName() {
			@Override
			public void toName(String name) {
				mapping.put(name, scopeAnnotation);
			}
		};
	}

	protected abstract void configuire();
}
