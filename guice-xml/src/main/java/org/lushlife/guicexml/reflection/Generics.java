package org.lushlife.guicexml.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Generics {
	static public Class<?> toRawType(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		}
		if (type instanceof ParameterizedType) {
			return toRawType(((ParameterizedType) type).getRawType());
		}
		throw new IllegalArgumentException("not support " + type);
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> createMap(Class<?> rawType) {
		try {
			if (Map.class.equals(rawType)) {
				return new LinkedHashMap<K, V>();
			}
			return (Map<K, V>) rawType.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> createList(Class<?> rawType) {
		try {
			if (List.class.equals(rawType)) {
				return new ArrayList<E>();
			}
			return (List<E>) rawType.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
