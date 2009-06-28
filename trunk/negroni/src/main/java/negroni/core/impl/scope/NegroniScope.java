package negroni.core.impl.scope;

import negroni.Enhancer;
import negroni.Identifier;

public interface NegroniScope {

	Object scoped(Identifier<?> id, Enhancer container, InstanceContext context);

}