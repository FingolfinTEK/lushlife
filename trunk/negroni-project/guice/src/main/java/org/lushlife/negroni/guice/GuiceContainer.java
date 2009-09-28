package org.lushlife.negroni.guice;

import java.lang.annotation.Annotation;

import org.lushlife.negroni.Container;

import com.google.inject.Injector;
import com.google.inject.ScopeAnnotation;

public class GuiceContainer implements Container {
	private Injector injector;

	public GuiceContainer(Injector injector) {
		this.injector = injector;
	}

	public <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	public boolean isManagementScope(Class<?> clazz) {
		for (Annotation annotation : clazz.getAnnotations()) {
			if (annotation.annotationType().isAnnotationPresent(
					ScopeAnnotation.class)) {
				return true;
			}
		}
		return false;
	}

}
