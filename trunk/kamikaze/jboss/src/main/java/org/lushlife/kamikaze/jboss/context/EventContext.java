package org.lushlife.kamikaze.jboss.context;

import org.jboss.webbeans.context.AbstractThreadLocalMapContext;
import org.lushlife.kamikaze.scope.EventScoped;

public class EventContext extends AbstractThreadLocalMapContext {

	static public EventContext INSTANCE = new EventContext();

	protected EventContext() {
		super(EventScoped.class);
	}

}
