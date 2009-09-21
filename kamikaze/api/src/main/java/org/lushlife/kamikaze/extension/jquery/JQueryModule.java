package org.lushlife.kamikaze.extension.jquery;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.resources.ResourceModule;

public class JQueryModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.model(JQueryResource.class);
		binder.install(new ResourceModule());
	}

}
