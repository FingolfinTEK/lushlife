package org.lushlife.kamikaze.extension.jquery;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.kamikaze.resources.ResourceModule;

public class JQueryModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.model(JQueryResource.class);
		binder.install(new ResourceModule());
	}

}
