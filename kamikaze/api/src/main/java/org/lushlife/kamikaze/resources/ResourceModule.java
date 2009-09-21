package org.lushlife.kamikaze.resources;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.kamikaze.extension.jspquery.JSPQueryModule;
import org.lushlife.kamikaze.spi.JaxRsServiceModle;
import org.lushlife.kamikaze.util.loader.ServiceLoader;

public class ResourceModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.install(ServiceLoader.load(JaxRsServiceModle.class).getSingle());
		binder.install(new JSPQueryModule());
		binder.model(ResourceCacheImpl.class);
		binder.model(ResourceManager.class);
	}

}
