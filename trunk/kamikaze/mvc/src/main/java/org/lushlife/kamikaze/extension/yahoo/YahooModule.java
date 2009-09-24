package org.lushlife.kamikaze.extension.yahoo;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.resources.ResourceModule;

public class YahooModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.model(YahooResource.class);
		binder.install(new ResourceModule());
	}

}
