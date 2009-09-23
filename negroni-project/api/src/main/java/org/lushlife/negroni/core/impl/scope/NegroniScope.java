package org.lushlife.negroni.core.impl.scope;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Identifier;

public interface NegroniScope {

	Object scoped(Identifier<?> id, Enhancer container, InstanceContext context);

}