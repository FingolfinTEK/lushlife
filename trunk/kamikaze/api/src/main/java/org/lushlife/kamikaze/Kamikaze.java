package org.lushlife.kamikaze;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.lushlife.kamikaze.context.Contexts;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class Kamikaze {

	static Log log = Logging.getLog(Kamikaze.class);

	static public <T> T $(Class<T> type, Annotation... bindings) {
		return getInjector().getInstance(type, bindings);
	}

	static public Injector getInjector() {
		BeanManager beanManager = Contexts.get(BeanManager.class);
		log.log(LogMsgCore.KMKZC0004, beanManager.hashCode(), beanManager);
		Set<Bean<?>> beans = beanManager.getBeans(Injector.class);
		Bean<? extends Object> injectorBean = beanManager.resolve(beans);
		return (Injector) beanManager.getReference(injectorBean,
				Injector.class, beanManager
						.createCreationalContext(injectorBean));
	}

}
