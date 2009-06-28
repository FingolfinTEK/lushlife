package negroni.core.closure;

public interface CR<RETURN> extends Closure<RETURN> {
	RETURN call();
}
