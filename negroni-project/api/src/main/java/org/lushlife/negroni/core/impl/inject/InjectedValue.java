package org.lushlife.negroni.core.impl.inject;

import org.lushlife.negroni.Enhancer;

public interface InjectedValue<T> {

	T getValue(Enhancer container);

}
