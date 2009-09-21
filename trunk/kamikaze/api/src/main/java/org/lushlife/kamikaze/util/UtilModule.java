package org.lushlife.kamikaze.util;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.kamikaze.util.date.StartupTimeProducer;
import org.lushlife.kamikaze.util.loader.ClassLoaderProducer;

public class UtilModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.model(ClassLoaderProducer.class);
		binder.model(StartupTimeProducer.class);
	}

}
