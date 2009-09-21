package org.lushlife.kamikaze;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

@javax.inject.Singleton
public class Injector implements Serializable {
	private static final long serialVersionUID = -4385238949949794479L;
	static Log log = Logging.getLog(Injector.class);

	@Inject
	public Injector(BeanManager manager) {
		super();
		this.manager = manager;
	}

	final private BeanManager manager;

	public <T> T $(Class<T> type, Annotation... bindings) {
		return getInstance(type, bindings);
	}

	public <T> T getInstance(Class<T> type, Annotation... bindings) {
		Set<Bean<?>> beans = manager.getBeans(type, bindings);
		Bean<T> bean = (Bean<T>) manager.resolve(beans);
		return (T) manager.getReference(bean, type, manager
				.createCreationalContext(bean));
	}

}
