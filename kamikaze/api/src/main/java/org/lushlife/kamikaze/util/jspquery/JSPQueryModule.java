package org.lushlife.kamikaze.util.jspquery;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;

public class JSPQueryModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.model(JSPQueryManager.class);
	}

}
