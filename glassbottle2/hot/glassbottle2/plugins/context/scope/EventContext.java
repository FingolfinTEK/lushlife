package glassbottle2.plugins.context.scope;

import org.jboss.webbeans.context.AbstractThreadLocalMapContext;

public class EventContext extends AbstractThreadLocalMapContext {

	static public EventContext INSTANCE = new EventContext();

	protected EventContext() {
		super(EventScoped.class);
	}

}
