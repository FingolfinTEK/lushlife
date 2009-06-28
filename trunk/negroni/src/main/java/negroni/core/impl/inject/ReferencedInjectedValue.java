package negroni.core.impl.inject;

import negroni.Enhancer;
import negroni.Identifier;

public class ReferencedInjectedValue<T> implements InjectedValue<T> {
	final Identifier<T> identifier;

	public ReferencedInjectedValue(Identifier<T> identifier) {
		this.identifier = identifier;
	}

	public T getValue(Enhancer container) {
		return container.getInstance(identifier);
	}

	public String toString() {
		return "id(ref=" + identifier + ")";
	}
}
