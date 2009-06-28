package negroni.core.closure;

public interface CV2<P1, P2> extends Closure<Void> {

	void call(P1 p1, P2 p2);

}
