package org.lushlife.negroni.core.impl.scope;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Identifier;

public class ContainerScope implements NegroniScope {

	public Object scoped(Identifier<?> id, Enhancer container,
			InstanceContext context) {
		return container.getInstance(id);
	}
}
