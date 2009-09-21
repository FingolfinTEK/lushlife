package org.lushlife.kamikaze;

import org.lushlife.kamikaze.context.ContextModule;
import org.lushlife.kamikaze.util.UtilModule;

public class CoreModule implements WebBeansModule {

	public void configure(WebBeansBinder module) {
		module.install(new ContextModule());
		module.install(new UtilModule());
		module.model(Injector.class);
	}

}
