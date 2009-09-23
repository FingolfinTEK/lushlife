package org.lushlife.negroni.core.closure;

public interface CR2<RETURN, P1, P2> extends Closure<RETURN> {

	RETURN call(P1 p1, P2 p2);

}
