package org.lushlife.kamikaze.extension.jspquery;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;

public class JSPQueryModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.model(JSPQueryManager.class);
	}

}
