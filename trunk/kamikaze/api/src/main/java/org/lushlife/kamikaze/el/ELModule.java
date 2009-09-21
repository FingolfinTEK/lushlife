package org.lushlife.kamikaze.el;

import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.kamikaze.el.impl.NamingResolverImpl;

public class ELModule implements BeanModule {

	public void configure(BeanBinder binder) {
		binder.model(ELVariableEventScopedMap.class);
		binder.model(NamingResolverImpl.class);
		binder.model(NamingResolverMap.class);
	}

}
