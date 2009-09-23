package org.lushlife.negroni.core.impl.scope;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Identifier;

public class InstanceScope implements NegroniScope {

	public Object scoped(Identifier<?> id, Enhancer container,
			InstanceContext context) {
		Object obj = context.getContext().get(id);
		if (obj == null) {
			obj = container.getInstance(id);
			context.getContext().put(id, obj);
		}
		return obj;
	}

}
