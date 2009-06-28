package negroni.core.impl.scope;

import negroni.Enhancer;
import negroni.Identifier;

public class ContainerScope implements NegroniScope {

	public Object scoped(Identifier<?> id, Enhancer container,
			InstanceContext context) {
		return container.getInstance(id);
	}
}
