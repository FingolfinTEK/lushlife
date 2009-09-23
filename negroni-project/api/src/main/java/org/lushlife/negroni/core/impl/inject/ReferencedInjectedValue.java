package org.lushlife.negroni.core.impl.inject;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Identifier;

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
