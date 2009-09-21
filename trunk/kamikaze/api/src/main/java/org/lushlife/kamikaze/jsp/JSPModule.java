package org.lushlife.kamikaze.jsp;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;

public class JSPModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.model(JSPPage.class);
	}

}
