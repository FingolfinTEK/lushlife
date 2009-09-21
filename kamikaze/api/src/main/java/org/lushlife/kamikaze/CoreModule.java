package org.lushlife.kamikaze;

import org.lushlife.kamikaze.context.ContextModule;
import org.lushlife.kamikaze.util.UtilModule;
import org.lushlife.kamikaze.util.id.RequestIdGenerator;

public class CoreModule implements BeanModule {

	public void configure(BeanBinder module) {
		module.install(new ContextModule());
		module.install(new UtilModule());
		module.model(KamikazeContext.class);
		module.model(Injector.class);
		module.model(RequestIdGenerator.class);
	}

}
