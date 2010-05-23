package org.lushlife.guicexml.reflection;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeImpl implements ParameterizedType {
	Class<?> rawType;
	Type[] actualType;

	public ParameterizedTypeImpl(Class<?> rawType, Type[] types) {
		this.rawType = rawType;
		this.actualType = types;
	}

	@Override
	public Type[] getActualTypeArguments() {
		return this.actualType;
	}

	@Override
	public Type getOwnerType() {
		return null;
	}

	@Override
	public Type getRawType() {
		return rawType;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(toString(rawType));
		sb.append("<");
		for (Type type : actualType) {
			sb.append(toString(type));
			sb.append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append(">");
		return sb.toString();
	}

	public String toString(Type type) {
		if (type instanceof Class<?>) {
			Class<?> clazz = (Class<?>) type;
			if (clazz.isArray()) {
				return toString(clazz.getComponentType()) + "[]";
			}
			return ((Class<?>) type).getName();
		}
		if (type instanceof GenericArrayType) {
			return toString(((GenericArrayType) type).getGenericComponentType())
					+ "[]";
		}
		return type.toString();
	}

}
