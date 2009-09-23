package negroni.core.impl.inject;

public interface Injection<T> {
	String getName();

	void setValue(Object owner, T value);

}
