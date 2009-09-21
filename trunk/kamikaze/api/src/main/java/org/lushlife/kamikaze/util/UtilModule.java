package org.lushlife.kamikaze.util;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.util.date.StartupTimeProducer;
import org.lushlife.kamikaze.util.id.RequestIdGenerator;
import org.lushlife.kamikaze.util.loader.ClassLoaderProducer;

public class UtilModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.model(ClassLoaderProducer.class);
		binder.model(StartupTimeProducer.class);
		binder.model(RequestIdGenerator.class);

	}

}
