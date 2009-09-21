package org.lushlife.kamikaze.el.impl;

import java.util.Set;

import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.lushlife.kamikaze.el.NamingResolver;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class NamingResolverImpl implements NamingResolver {
	Log log = Logging.getLog(NamingResolverImpl.class);

	@Inject
	BeanManager manager;

	@Inject
	ClassLoader loader;

	public Bean<?> resolve(String name) {
		Bean<?> bean = null;
		if (bean == null) {
			bean = resolveByBeanManager(name);
		}

		return bean;
	}

	private Bean<?> resolveByBeanManager(String name) {
		Set<Bean<?>> beans = manager.getBeans(name);
		return validate(name, beans);
	}

	private Bean<?> validate(String name, Set<Bean<?>> beans) {
		if (beans.size() == 0) {
			return null;
		}
		if (beans.size() > 1) {
			throw new AmbiguousResolutionException(name
					+ " Resolved multiple Web Beans " + beans);
		}
		return beans.iterator().next();
	}

}
