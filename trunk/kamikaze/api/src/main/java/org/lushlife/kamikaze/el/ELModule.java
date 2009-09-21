package org.lushlife.kamikaze.el;

import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.el.impl.NamingResolverImpl;

public class ELModule implements WebBeansModule {

	public void configure(WebBeansBinder binder) {
		binder.model(ELVariableEventScopedMap.class);
		binder.model(NamingResolverImpl.class);
		binder.model(NamingResolverMap.class);
	}

}
