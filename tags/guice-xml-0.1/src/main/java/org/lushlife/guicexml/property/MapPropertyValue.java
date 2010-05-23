package org.lushlife.guicexml.property;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.reflection.Generics;

public class MapPropertyValue implements PropertyValue {

	private Map<PropertyValue, PropertyValue> value;

	public MapPropertyValue(Map<PropertyValue, PropertyValue> value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		MapPropertyValue other = (MapPropertyValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public Object resolveString(Type type, Expressions expressions) {
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Class<?> rawType = Generics.toRawType(parameterizedType
					.getRawType());
			if (!Map.class.isAssignableFrom(rawType)) {
				throw new IllegalArgumentException("unsupported type " + type);
			}
			Type keyType = parameterizedType.getActualTypeArguments()[0];
			Type valueType = parameterizedType.getActualTypeArguments()[1];
			return createMap(rawType, keyType, valueType, expressions);
		}
		if (type instanceof Class<?>) {
			Class<?> clazz = (Class<?>) type;
			if (Map.class.isAssignableFrom(clazz)) {
				return createMap(clazz, String.class, String.class, expressions);
			}
		}
		throw new IllegalArgumentException("unsupported type " + type);

	}

	private Object createMap(Class<?> rawType, Type keyType, Type valueType,
			Expressions expressions) {
		Map<Object, Object> map = Generics.createMap(rawType);
		for (Entry<PropertyValue, PropertyValue> entry : value.entrySet()) {
			map.put(entry.getKey().resolveString(keyType, expressions), entry
					.getValue().resolveString(valueType, expressions));
		}
		return map;
	}

	public String toString() {
		return value.toString();
	}
}
