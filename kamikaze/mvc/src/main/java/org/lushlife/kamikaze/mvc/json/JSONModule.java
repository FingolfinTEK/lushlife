package org.lushlife.kamikaze.mvc.json;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;

public class JSONModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.model(JSONWriter.class);
	}

}
