package negroni.core.impl.inject;

import negroni.Enhancer;

public interface InjectedValue<T> {

	T getValue(Enhancer container);

}
