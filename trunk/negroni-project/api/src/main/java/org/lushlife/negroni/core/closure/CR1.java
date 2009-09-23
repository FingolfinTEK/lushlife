package org.lushlife.negroni.core.closure;

public interface CR1<RETURN, P> extends Closure<RETURN> {
	RETURN call(P p1);
}
