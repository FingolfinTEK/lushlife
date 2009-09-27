package org.lushlife.negroni.jsr299;

import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.lushlife.negroni.Container;

public class BeanManagerContainer implements Container {
	private BeanManager beanManager;

	public <T> T getInstance(Class<T> clazz) {
		Set<Bean<?>> beans = beanManager.getBeans(clazz);
		Bean<? extends Object> bean = beanManager.resolve(beans);
		if (bean == null) {
			throw new NullPointerException("BeanManager don't have a bean("
					+ clazz + ")");
		}
		return (T) beanManager.getReference(bean, clazz, beanManager
				.createCreationalContext(bean));
	}

	public BeanManagerContainer(BeanManager beanManager) {
		super();
		this.beanManager = beanManager;
	}

}
