package org.lushlife.kamikaze.mvc.jsp;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;

public class JSPModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.model(JSPPage.class);
	}

}
