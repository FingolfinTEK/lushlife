package org.lushlife.kamikaze.resources;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.spi.JaxRsServiceModle;
import org.lushlife.kamikaze.util.UtilModule;

public class ResourceModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.installService(JaxRsServiceModle.class);
		binder.install(new UtilModule());
		binder.model(ResourceCacheImpl.class);
	}
}
