package org.lushlife.kamikaze.jboss.bootstrap;

import org.jboss.webbeans.util.serviceProvider.ServiceLoader;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class ModuleLoader {

	static Log log = Logging.getLog(ModuleLoader.class);

	static public Iterable<BeanModule> loadModules(ClassLoader loader) {
		return ServiceLoader.load(BeanModule.class, loader);
	}
}
