package org.lushlife.kamikaze.extension.yahoo;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.kamikaze.resources.ResourceModule;

public class YahooModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.model(YahooResource.class);
		binder.install(new ResourceModule());
	}

}
