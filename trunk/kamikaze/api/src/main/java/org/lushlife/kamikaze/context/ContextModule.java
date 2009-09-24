package org.lushlife.kamikaze.context;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.spi.ContextServiceModule;

public class ContextModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.installService(ContextServiceModule.class);
		binder.model(Contexts.class);
	}
}
