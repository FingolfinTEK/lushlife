package org.lushlife.negroni.core.impl.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.annotation.MixinImplementedBy;
import org.lushlife.negroni.annotation.MustImplement;
import org.lushlife.negroni.annotation.Undefined;
import org.lushlife.negroni.core.exceptions.ConfigurationException;
import org.lushlife.negroni.core.exceptions.NegroniException;
import org.lushlife.negroni.core.exceptions.RuntimeInvocationTargetException;
import org.lushlife.negroni.core.impl.ConfiguratorBase;
import org.lushlife.negroni.core.impl.conversions.Conversions;
import org.lushlife.negroni.core.impl.delegate.DelegateMethod;
import org.lushlife.negroni.core.impl.delegate.DelegateMethodFactory;
import org.lushlife.negroni.core.impl.delegate.annotation.Precedence;



public class Reflections {

	public static URL getResouce(String resouceName) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				resouceName);
		if (url == null) {
			url = Negroni.class.getResource(resouceName);
		}
		return url;
	}

	static public <T> HashMap<MethodId, DelegateMethod> createMapping(
			Class owner, Class<T> interfces, ConfiguratorBase creator) {
		List<DelegateMethod> dd = DelegateMethodFactory.getDelegateMethods(
				interfces, creator);
		return Reflections.createMapping(owner, interfces, dd);
	}

	static public void handleException(Method thisMethod,
			RuntimeInvocationTargetException e) throws Throwable {
		Throwable cause = e.getCause();
		if (cause instanceof InvocationTargetException) {
			cause = cause.getCause();
			for (Class ex : thisMethod.getExceptionTypes()) {
				if (ex.isAssignableFrom(cause.getClass())) {
					throw cause;
				}
			}
		}
	}

	static public Object invoke(Object owner, Method m, Object[] args) {
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
			throw new RuntimeInvocationTargetException(e);
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
			if (c.isAnnotationPresent(MixinImplementedBy.class)) {
				for (Class<?> cc : c.getAnnotation(MixinImplementedBy.class)
						.value()) {
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

		for (Method m : methodSet) {
			if (m.isAnnotationPresent(MustImplement.class)) {
				throw new NegroniException("must overwrite " + m);
			}
		}

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

	static public Set<Method> getMethodsPresentByAnnotations(Class<?> clazz,
			Class<? extends Annotation>... annotations) {
		Set<Method> method = new HashSet<Method>();
		METHODROOP: for (Method m : clazz.getMethods()) {
			for (Class<? extends Annotation> an : annotations) {
				if (!m.isAnnotationPresent(an)) {
					continue METHODROOP;
				}
			}
			method.add(m);
		}
		return method;
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

	static private HashMap<MethodId, DelegateMethod> createMapping(Class owner,
			Class<?> interfaces, List<DelegateMethod> dd) {
		HashMap<MethodId, DelegateMethod> mapping = new HashMap<MethodId, DelegateMethod>();
		Set<Method> undefineMethod = Reflections.undifinedMethods(interfaces);
		for (Method m : undefineMethod) {
			ArrayList<DelegateMethod> acceptsMethods = new ArrayList<DelegateMethod>();
			for (DelegateMethod delegate : dd) {
				if (delegate.isAccept(owner, m)) {
					acceptsMethods.add(delegate);
				}
			}
			if (acceptsMethods.size() == 0) {
				throw new ConfigurationException(
						"mapping not found clazz=" + owner + " \t method=" + m);
			}
			// 優先順位制御
			DelegateMethod maxPrecedence = Collections.min(acceptsMethods);

			// for (DelegateMethod d : acceptsMethods) {
			// System.out.println(m + "\t" + d.getDelegateMethod());
			// }
			// System.out.println("win \t" + m + "\t" + maxPrecedence);
			// System.out.println();

			mapping.put(new MethodId(m), maxPrecedence);
		}
		return mapping;
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
		{
			if (fromNoVarargsParams.length < toNoVarargsParams.length) {
				return false;
			}
			for (int i = 0; i < toNoVarargsParams.length; i++) {
				if (!Conversions.isConvert(fromNoVarargsParams[i],
						toNoVarargsParams[i])) {
					return false;
				}
			}
		}

		// 可変長引数の型チェック
		for (int i = toNoVarargsParams.length; i < fromNoVarargsParams.length; i++) {
			if (!Conversions.isConvert(fromNoVarargsParams[i], toVarargsParam)) {
				return false;
			}
		}
		return true;

		// Class<?>[] params = from.getParameterTypes();
		// Class<?>[] thisParams = to.getParameterTypes();
		//
		// if (params.length < thisParams.length - pos - 1) {
		// return false;
		// }
		//
		// for (int i = pos; i < thisParams.length - 1; i++) {
		// if (!Conversions.isConvert(params[i - pos], thisParams[i])) {
		// return false;
		// }
		// }
		// Class<?> varArgs = thisParams[thisParams.length - 1];
		//
		// for (int i = thisParams.length - 1; i < params.length - 1; i++) {
		// if (!Conversions.isConvert(params[i - 1], varArgs)) {
		// return false;
		// }
		// }
		//
		// // varargs to varargs
		// if (from.isVarArgs()) {
		// Class<?> fromVarArgs = params[params.length - 1];
		// if (!Conversions.isConvert(fromVarArgs, varArgs)) {
		// return false;
		// }
		// }
		//
		// return true;
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

	static public String toTypeId(Method m) {
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
		if (m.isAnnotationPresent(Undefined.class)) {
			isUndefined = true;
		}
		if ((m.getModifiers() & java.lang.reflect.Modifier.ABSTRACT) != 0) {
			isUndefined = true;
		}
		return isUndefined;
	}

	public static Method getMethod(Class<?> clazz, String name,
			Class<?>... parameterTypes) {
		try {
			return clazz.getMethod(name, parameterTypes);
		} catch (Exception e) {
			throw new NegroniException(e);
		}
	}

	public static void set(Object owner, Field f, Object value) {
		try {
			f.set(owner, value);
		} catch (Exception e) {
			throw new NegroniException("field is " + f.getDeclaringClass()
					+ " name " + f.getName(), e);
		}
	}

	public static <T extends Annotation> T getEnumAnnotation(Enum<?> e,
			Class<T> anotation) {
		try {
			return e.getDeclaringClass().getDeclaredField(e.name())
					.getAnnotation(anotation);
		} catch (Exception ex) {
			throw new NegroniException(ex);
		}
	}

	public static Object get(Object e, Field f) {
		try {
			return f.get(e);
		} catch (Exception ex) {
			throw new NegroniException(ex);
		}
	}

	public static BeanInfo getBeanInfo(Class<?> clazz) {
		try {
			return Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			throw new NegroniException(e);
		}
	}

	public static Field getField(Class<?> clazz, String name) {
		while (clazz != null) {
			try {
				return clazz.getDeclaredField(name);
			} catch (Exception e) {
				clazz = clazz.getSuperclass();
				if (clazz == null) {
					throw new NegroniException(e);
				} else {
					continue;
				}
			}
		}
		throw new NegroniException("field not found [class=" + clazz + ",field="
				+ name + "]");
	}

	public static Field[] getEnumFields(Enum<?> e) {
		try {
			Class clazz = e.getClass();
			if (clazz.equals(e.getDeclaringClass())) {
				return new Field[0];
			} else {
				return clazz.getDeclaredFields();
			}
		} catch (Exception ex) {
			throw new NegroniException(ex);
		}
	}

	public static boolean isEnumAnnotationPresent(Enum<?> e,
			Class<? extends Annotation> an) {
		try {
			return e.getDeclaringClass().getField(e.name())
					.isAnnotationPresent(an);
		} catch (Exception ex) {
			throw new NegroniException(ex);
		}
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

	public static Object[] annotationValues(Annotation an) {
		Class<?> clazz = an.annotationType();
		ArrayList<Object> obj = new ArrayList<Object>();
		for (Method m : clazz.getMethods()) {
			if (m.getDeclaringClass().equals(clazz)) {
				obj.add(Reflections.invoke(an, m, new Object[0]));
			}
		}
		return obj.toArray();
	}

	public static Object newInstnace(String className) {
		return Reflections.newInstnace(forName(className));
	}

	private static Object newInstnace(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new NegroniException("newInstnace failed " + clazz, e);
		}
	}

	public static Class<?> forName(String className) {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(
					className);
		} catch (ClassNotFoundException e) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e1) {
				throw new NegroniException("class not found " + className, e1);
			}
		}
	}
}
