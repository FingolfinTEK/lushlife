package org.lushlife.kamikaze.json;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;

public class JSONModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.model(JSONWriter.class);
	}

}
