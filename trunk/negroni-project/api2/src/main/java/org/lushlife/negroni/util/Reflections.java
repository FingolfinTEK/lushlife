package org.lushlife.negroni.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.lushlife.negroni.Mixined;
import org.lushlife.negroni.conversions.Conversions;
import org.lushlife.negroni.delegate.DelegateMethod;
import org.lushlife.negroni.delegate.Precedence;

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
		int aValue = a.getClass().getAnnotation(Precedence.class).value();
		int bValue = b.getClass().getAnnotation(Precedence.class).value();
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

}
