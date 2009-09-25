package org.lushlife.kamikaze;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.lushlife.kamikaze.context.Contexts;
import org.lushlife.kamikaze.spi.BootstrapService;
import org.lushlife.kamikaze.util.loader.ServiceLoader;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class Kamikaze {

	static Log log = Logging.getLog(Kamikaze.class);

	static public <T> T $(Class<T> type, Annotation... bindings) {
		return getInjector().getInstance(type, bindings);
	}

	static private BootstrapService bootService;
	static {
		bootService = ServiceLoader.load(BootstrapService.class).getSingle();
	}

	static public void bootInjector(WebBeansModule... modules) {

		bootService.bootManager(Arrays.asList(modules));
	}

	static public void bootInjector(Iterable<WebBeansModule> modules) {
		bootService.bootManager(modules);
	}

	static public void shutdown() {
		bootService.shutdownManager();
	}

	static public Injector getInjector() {
		BeanManager beanManager = Contexts.get(BeanManager.class);
		if (beanManager == null) {
			new NullPointerException(Logging.getMessage(LogMsgKMKZC.KMKZC0009));
		}
		log.log(LogMsgKMKZC.KMKZC0004, beanManager.hashCode(), beanManager);
		Set<Bean<?>> beans = beanManager.getBeans(Injector.class);
		Bean<? extends Object> injectorBean = beanManager.resolve(beans);
		if (injectorBean == null) {
			throw new NullPointerException(Logging.getMessage(
					LogMsgKMKZC.KMKZC0010, Injector.class));
		}
		return (Injector) beanManager.getReference(injectorBean,
				Injector.class, beanManager
						.createCreationalContext(injectorBean));
	}

	static public BeanManager getBeanManager() {
		return Contexts.get(BeanManager.class);
	}

}
