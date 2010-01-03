package org.lushlife.inject;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.jboss.weld.Container;

public class Injector implements Serializable {
	private static final long serialVersionUID = -8581706590320273960L;

	static public <T> T $(Class<T> clazz, Annotation... annotations) {
		return get(clazz, annotations);
	}

	static public <T> T get(Class<T> clazz, Annotation... annotations) {
		return new Injector(Container.instance().deploymentManager())
				.getInstnace(clazz, annotations);
	}

	@Inject
	public Injector(BeanManager beanManager) {
		this.beanManager = beanManager;
	}

	private BeanManager beanManager;

	public <T> T getInstnace(Class<T> clazz, Annotation... annotations) {
		Set<Bean<?>> beans = beanManager.getBeans(clazz, annotations);
		if (beans.isEmpty()) {
			throw new NullPointerException("bean is not found. " + clazz
					+ " : " + Arrays.toString(annotations));
		}

		if (beans.size() > 1) {
			throw new AmbiguousResolutionException("bean is ambiguous. "
					+ clazz + " : " + Arrays.toString(annotations));
		}

		Bean<?> bean = beans.iterator().next();
		return (T) beanManager.getReference(bean, clazz, beanManager
				.createCreationalContext(bean));

	}
}
