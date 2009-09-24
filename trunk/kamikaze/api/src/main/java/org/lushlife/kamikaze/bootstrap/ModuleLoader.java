package org.lushlife.kamikaze.bootstrap;

import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.util.loader.ServiceLoader;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class ModuleLoader {

	static Log log = Logging.getLog(ModuleLoader.class);

	static public Iterable<WebBeansModule> loadModules(ClassLoader loader) {
		return ServiceLoader.load(WebBeansModule.class, loader);
	}
}
