package org.lushlife.kamikaze.resources;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.extension.jspquery.JSPQueryModule;
import org.lushlife.kamikaze.spi.JaxRsServiceModle;

public class ResourceModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.installService(JaxRsServiceModle.class);
		binder.install(new JSPQueryModule());
		binder.model(ResourceCacheImpl.class);
	}
}
