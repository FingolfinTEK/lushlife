package org.lushlife.kamikaze.context;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.kamikaze.spi.ContextServiceModule;
import org.lushlife.kamikaze.util.loader.ServiceLoader;

public class ContextModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.install(ServiceLoader.load(ContextServiceModule.class)
				.getSingle());
		binder.model(ServletPathInfo.class);
	}

}
