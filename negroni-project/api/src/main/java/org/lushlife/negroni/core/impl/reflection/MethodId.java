package org.lushlife.negroni.core.impl.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodId {
	// final Method mehtod;
	String name;
	Class<?>[] parameterTypes;
	Class<?> returnType;

	public MethodId(Method method) {
		super();
		this.name = method.getName();
		this.parameterTypes = method.getParameterTypes();
		this.returnType = method.getReturnType();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(parameterTypes);
		result = prime * result
				+ ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MethodId other = (MethodId) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(parameterTypes, other.parameterTypes))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		return true;
	}

}
