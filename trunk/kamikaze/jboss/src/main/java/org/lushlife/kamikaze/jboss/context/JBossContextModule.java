package org.lushlife.kamikaze.jboss.context;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.spi.ContextServiceModule;

public class JBossContextModule implements ContextServiceModule {

	public void configure(WebBeansBinder binder) {
		binder.model(ContextListener.class);
	}

}
