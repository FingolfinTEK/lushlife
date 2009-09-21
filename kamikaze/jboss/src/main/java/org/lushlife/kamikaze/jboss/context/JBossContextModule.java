package org.lushlife.kamikaze.jboss.context;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.spi.ContextServiceModule;

public class JBossContextModule implements ContextServiceModule {

	public void configure(BeanBinder binder) {
		binder.model(ContextListener.class);
	}

}
