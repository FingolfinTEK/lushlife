package org.lushlife.kamikaze.mvc.jboss.jaxrs;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.spi.JaxRsServiceModle;

public class JaxRSModule implements JaxRsServiceModle {

	public void configure(WebBeansBinder binder) {
		binder.model(JaxRSInitializer.class);
		binder.model(JaxRsServletDispatcher.class);
	}

}