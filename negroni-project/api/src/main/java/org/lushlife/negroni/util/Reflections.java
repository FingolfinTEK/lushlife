/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.lushlife.negroni.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.lushlife.negroni.MixinInterface;
import org.lushlife.negroni.Mixined;
import org.lushlife.negroni.conversions.Conversions;
import org.lushlife.negroni.delegate.DelegateMethod;
import org.lushlife.negroni.delegate.DelegateMethodPrecedence;

/**
 * @author Takeshi Kondo
 */
public class Reflections {

	static public Object invoke(Object owner, Method m, Object[] args)
			throws Exception {
		try {
			return m.invoke(owner, args);
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			if (e instanceof InvocationTargetException) {
				Throwable cause = e.getCause();
				if (cause instanceof RuntimeException) {
					throw (RuntimeException) cause;
				}
				if (cause instanceof Error) {
					throw (Error) cause;
				}
			}
			throw e;
		}
	}

	static public Object[] toSimpleTypeArgs(Object[] owner, Object[] args) {
		Object[] tmp = new Object[owner.length + args.length];
		System.arraycopy(owner, 0, tmp, 0, owner.length);
		System.arraycopy(args, 0, tmp, owner.length, args.length);
		return tmp;
	}

	static public Object[] toVarargsArgs(Object[] owner, Object[] args,
			int pos, Class<?> type) {
		Object[] tmp = new Object[pos + 1];
		System.arraycopy(owner, 0, tmp, 0, owner.length);
		System.arraycopy(args, 0, tmp, owner.length, pos - owner.length);
		tmp[tmp.length - 1] = varargsToArray(args, pos - owner.length, type);
		return tmp;
	}

	private static Object[] varargsToArray(Object[] args,
			int varArgsStartpoint, Class<?> type) {
		if (args.length < varArgsStartpoint) {
			return (Object[]) Array.newInstance(type, 0);
		}
		Object[] varArgs = (Object[]) Array.newInstance(type, args.length
				- varArgsStartpoint);
		System.arraycopy(args, varArgsStartpoint, varArgs, 0, varArgs.length);
		return varArgs;
	}

	static public int classCompare(DelegateMethod a, DelegateMethod b) {
		int aValue = a.getClass().getAnnotation(DelegateMethodPrecedence.class)
				.value();
		int bValue = b.getClass().getAnnotation(DelegateMethodPrecedence.class)
				.value();
		return aValue - bValue;
	}

	static public Set<Class<?>> mixinImplementClasses(Class<?> clazz) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> c : toAllSuperClassAndInterface(clazz)) {
			if (c.isAnnotationPresent(Mixined.class)) {
				for (Class<?> cc : c.getAnnotation(Mixined.class).value()) {
					classes.add(cc);
				}
			}
		}
		return classes;
	}

	static public Set<Method> undifinedMethods(Class<?> clazz) {
		Set<Method> allMethod = allMethod(clazz);
		HashMap<String, Method> methodMap = new HashMap<String, Method>();
		for (Method m : allMethod) {
			if (Reflections.isUndefined(m)) {
				methodMap.put(Reflections.toTypeId(m), m);
			}
		}
		for (Method m : allMethod) {
			if (!Reflections.isUndefined(m)) {
				String id = Reflections.toTypeId(m);
				if (methodMap.containsKey(id)) {
					methodMap.remove(id);
				}
			}
		}
		HashSet<Method> methodSet = new HashSet<Method>();
		methodSet.addAll(methodMap.values());
		return methodSet;
	}

	static public Set<Class<?>> toAllSuperClassAndInterface(Class<?> clazz) {
		HashSet<Class<?>> allClass = new HashSet<Class<?>>();
		LinkedList<Class<?>> list = new LinkedList<Class<?>>();
		list.add(clazz);
		while (!list.isEmpty()) {
			Class<?> c = list.poll();
			allClass.add(c);
			Class<?> superClass = c.getSuperclass();
			if (superClass != null) {
				list.add(superClass);
			}
			for (Class<?> itf : c.getInterfaces()) {
				list.add(itf);
			}
		}
		return allClass;
	}

	static public Set<Method> allMethod(Class<?> clazz) {
		HashSet<Method> methods = new HashSet<Method>();
		LinkedList<Class<?>> list = new LinkedList<Class<?>>();
		list.add(clazz);
		while (!list.isEmpty()) {
			Class<?> c = list.poll();
			for (Method m : c.getDeclaredMethods()) {
				methods.add(m);
			}
			Class<?> superClass = c.getSuperclass();
			if (superClass != null) {
				list.add(superClass);
			}
			for (Class<?> itf : c.getInterfaces()) {
				list.add(itf);
			}
		}
		return methods;
	}

	public static boolean isSimpleTypeAccept(Method from, Method to, int pos) {
		Class<?>[] params = from.getParameterTypes();
		Class<?>[] thisParams = to.getParameterTypes();
		if (params.length + pos != thisParams.length) {
			return false;
		}
		for (int i = pos; i < thisParams.length; i++) {
			if (!Conversions.isConvert(params[i - pos], thisParams[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean isVarArgsAccept(Method from, Method to, int pos) {
		// varargs以外は対象としない
		if (!to.isVarArgs()) {
			return false;
		}
		Class<?>[] toNoVarargsParams = noVarargsParams(to, pos);
		Class<?> toVarargsParam = varargsParam(to);
		Class<?>[] fromNoVarargsParams = noVarargsParams(from, 0);
		Class<?> fromVarargsParam = varargsParam(from);

		// 可変長引数でかつ、キャストすることが出来ない場合は受け入れない
		if (fromVarargsParam != null
				&& !Conversions.isConvert(fromVarargsParam, toVarargsParam)) {
			return false;
		}

		// 引数の型チェック
		if (fromNoVarargsParams.length < toNoVarargsParams.length) {
			return false;
		}
		for (int i = 0; i < toNoVarargsParams.length; i++) {
			if (!Conversions.isConvert(fromNoVarargsParams[i],
					toNoVarargsParams[i])) {
				return false;
			}
		}

		// 可変長引数の型チェック
		for (int i = toNoVarargsParams.length; i < fromNoVarargsParams.length; i++) {
			if (!Conversions.isConvert(fromNoVarargsParams[i], toVarargsParam)) {
				return false;
			}
		}
		return true;
	}

	private static Class<?>[] noVarargsParams(Method to, int pos) {
		Class<?>[] types = to.getParameterTypes();
		if (to.isVarArgs()) {
			Class<?>[] temp = new Class[types.length - pos - 1];
			System.arraycopy(types, pos, temp, 0, temp.length);
			return temp;
		} else {
			Class<?>[] temp = new Class[types.length - pos];
			System.arraycopy(types, pos, temp, 0, temp.length);
			return temp;
		}
	}

	private static Class<?> varargsParam(Method to) {
		if (!to.isVarArgs()) {
			return null;
		}
		Class<?>[] types = to.getParameterTypes();
		return types[types.length - 1].getComponentType();
	}

	public static boolean isAccept(Method from, Method to, int pos) {
		if (isSimpleTypeAccept(from, to, pos)) {
			return true;
		}
		if (isVarArgsAccept(from, to, pos)) {
			return true;
		}
		return false;
	}

	static private String toTypeId(Method m) {
		StringBuilder sb = new StringBuilder();
		sb.append(m.getReturnType());
		sb.append(":");
		sb.append(m.getName());
		for (Class<?> c : m.getParameterTypes()) {
			sb.append(",");
			sb.append(c);
		}
		return sb.toString();
	}

	public static int methodCompare(Method a, Method b) {
		boolean ab = isAccept(a, b, 0);
		boolean ba = isAccept(b, a, 0);
		if (ab && ba) {
			return 0;
		}
		if (ab) {
			return -1;
		}
		if (ba) {
			return 1;
		}
		return a.getName().compareTo(b.getName());

	}

	static public boolean isUndefined(Method m) {
		boolean isUndefined = false;
		if ((m.getModifiers() & java.lang.reflect.Modifier.ABSTRACT) != 0) {
			isUndefined = true;
		}
		return isUndefined;
	}

	public static Object[] varargsFlatten(Object[] args) {
		if (args.length == 0) {
			return args;
		}
		Object varargs = args[args.length - 1];
		int size = Array.getLength(varargs);
		Object[] temp = new Object[args.length - 1 + size];
		System.arraycopy(args, 0, temp, 0, args.length - 1);
		Class<?> componentType = varargs.getClass().getComponentType();
		boolean isPrimitive = componentType.isPrimitive();
		if (isPrimitive) {
			for (int i = 0; i < size; i++) {
				temp[i + args.length - 1] = Conversions.getConverters().get(
						componentType).convert(Array.get(varargs, i));
			}
		} else {
			System.arraycopy(varargs, 0, temp, args.length - 1, size);
		}
		return temp;
	}

	public static int searchParameterAnnotation(Method m,
			Class<? extends Annotation> c) {
		Annotation[][] annotations = m.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			for (Annotation annotation : annotations[i]) {
				if (annotation.annotationType().equals(c)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static Type findActualType(Type type, TypeVariable<?> typeVariable) {
		if (type instanceof Class<?>) {
			Type findType = findActualType(((Class<?>) type)
					.getGenericSuperclass(), typeVariable);
			if (findType != null) {
				return findType;
			}
			for (Type interfaceType : ((Class<?>) type).getGenericInterfaces()) {
				findType = findActualType(interfaceType, typeVariable);
				if (findType != null) {
					return findType;
				}
			}
		}
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Class<?> rawType = toClass(parameterizedType.getRawType());
			for (int i = 0; i < rawType.getTypeParameters().length; i++) {
				if (rawType.getTypeParameters()[i].equals(typeVariable)) {
					return parameterizedType.getActualTypeArguments()[i];
				}
			}
		}
		return null;
	}

	public static Class<?> getActualMixinType(Class<?> mixinClass) {
		Type type = getMixinInstanceType(mixinClass);
		return findActualType(mixinClass, type);
	}

	private static Class<?> findActualType(Class<?> mixinClass, Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		}
		if (type instanceof ParameterizedType) {
			return findActualType(mixinClass, ((ParameterizedType) type)
					.getRawType());
		}
		if (type instanceof TypeVariable<?>) {
			Type findType = findActualType(mixinClass.getGenericSuperclass(),
					(TypeVariable<?>) type);
			if (findType != null) {
				return findActualType(mixinClass, findType);
			}
			for (Type interfaceType : mixinClass.getGenericInterfaces()) {
				findType = findActualType(interfaceType, (TypeVariable<?>) type);
				if (findType != null) {
					return findActualType(mixinClass, findType);
				}
			}

		}
		return null;
	}

	public static Type getMixinInstanceType(Class<?> mixinClass) {
		if (!MixinInterface.class.isAssignableFrom(mixinClass)) {
			return null;
		}
		Type type = getMixinInstanceType(mixinClass.getGenericSuperclass());
		if (type != null) {
			return type;
		}
		for (Type interfaces : mixinClass.getGenericInterfaces()) {
			type = getMixinInstanceType(interfaces);
			if (type != null) {
				return type;
			}
		}
		return null;
	}

	private static Type getMixinInstanceType(Type type) {
		if (type instanceof Class<?>) {
			return getMixinInstanceType((Class<?>) type);
		}
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Class<?> rawType = toClass(parameterizedType.getRawType());
			if (rawType.equals(MixinInterface.class)) {
				return parameterizedType.getActualTypeArguments()[0];
			}
			return getMixinInstanceType(rawType);
		}
		// TODO Auto-generated method stub
		return null;
	}

	private static Class<?> toClass(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		}
		if (type instanceof ParameterizedType) {
			return toClass(((ParameterizedType) type).getRawType());
		}
		return null;
	}

}
