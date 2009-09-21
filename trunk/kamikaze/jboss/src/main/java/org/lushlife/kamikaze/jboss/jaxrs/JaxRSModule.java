package org.lushlife.kamikaze.jboss.jaxrs;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.spi.JaxRsServiceModle;

public class JaxRSModule implements JaxRsServiceModle {

	public void configure(BeanBinder binder) {
		binder.model(JaxRSInitializer.class);
		binder.model(JaxRsServletDispatcher.class);
	}

}
