package org.lushlife.negroni.core.impl.guice;

import org.lushlife.negroni.Identifier;
import org.lushlife.negroni.core.impl.scope.ContainerScope;
import org.lushlife.negroni.core.impl.scope.NegroniScope;

import com.google.inject.Key;

class GuiceIdentifer<T> implements Identifier<T> {

	final Key<T> key;
	NegroniScope scope;

	public GuiceIdentifer(Key<T> key) {
		super();
		this.key = key;
		this.scope = createScope();
	}

	public Key<T> getKey() {
		return key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GuiceIdentifer other = (GuiceIdentifer) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	public NegroniScope getScope() {
		return scope;
	}

	private NegroniScope createScope() {
		return new ContainerScope();
	}

	// public boolean isPerinstanceScoped() {
	// Type type = key.getTypeLiteral().getType();
	// if (type instanceof Class<?>) {
	// return ((Class<?>) type).isAnnotationPresent(InstanceScoped.class);
	// } else {
	// return false;
	// }
	//	}

	public String toString() {
		return "GuiceId(key=" + key.toString() + ",scope=" + scope + ")";
	}

}
