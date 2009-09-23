package negroni.core.impl.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import negroni.Enhancer;
import negroni.core.exceptions.NegroniException;
import negroni.core.exceptions.RuntimeInvocationTargetException;
import negroni.core.impl.delegate.DelegateMethod;
import negroni.core.impl.scope.InstanceContext;
import negroni.core.impl.scope.NegroniContext;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;


class Javassists {
	static ConcurrentMap<Class<?>, Class<? extends ProxyObject>> cache = new ConcurrentHashMap<Class<?>, Class<? extends ProxyObject>>();

	@SuppressWarnings("unchecked")
	static public Class<? extends ProxyObject> getProxyFactory(Class<?> clazz) {
		if (cache.containsKey(clazz)) {
			return cache.get(clazz);
		}
		ProxyFactory factory = new ProxyFactory();
		Set<Method> abstractSet = Reflections.undifinedMethods(clazz);
		factory.setFilter(new TargetMethodFilter(abstractSet));
		if (clazz.isInterface()) {
			factory.setInterfaces(new Class[] { clazz });
		} else {
			factory.setSuperclass(clazz);
		}
		Class<? extends ProxyObject> c = factory.createClass();
		cache.put(clazz, c);
		return c;
	}

	static public class DelegateLatteMethodHandler implements MethodHandler {

		Enhancer container;
		Map<MethodId, DelegateMethod> mapping;

		// メモリリークを防ぐためには、ここに置くしかないか・・・・
		InstanceContext perinstanceScope;

		public DelegateLatteMethodHandler(Enhancer container,
				Map<MethodId, DelegateMethod> mapping, Object owner) {
			this.container = container;
			this.mapping = mapping;
			this.perinstanceScope = new InstanceContext(owner);
		}

		public Object invoke(Object self, Method thisMethod, Method proceed,
				Object[] args) throws Throwable {
			ConcurrentHashMap<Object, Object> contextMap = NegroniContext.instanceScope
					.get();
			try {
				NegroniContext.instanceScope.set(perinstanceScope.getContext());
				return mapping.get(new MethodId(thisMethod)).invoke(container,
						perinstanceScope.getOwner(), thisMethod, args,
						perinstanceScope);
			} catch (RuntimeInvocationTargetException e) {
				Reflections.handleException(thisMethod, e);
				throw e;
			} finally {
				NegroniContext.instanceScope.set(contextMap);
			}
		}
	}

	@SuppressWarnings("unchecked")
	static public <T> T createProxy(Object obj, Class<T> clazz,
			Enhancer container, HashMap<MethodId, DelegateMethod> mapping) {
		try {
			Class<? extends ProxyObject> proxyClass = getProxyFactory(clazz);
			ProxyObject po = container.getInstance(proxyClass);
			MethodHandler handler = new DelegateLatteMethodHandler(container,
					mapping, obj);
			po.setHandler(handler);
			return (T) po;
		} catch (Exception e) {
			throw new NegroniException(e);
		}
	}

	static public class LatteMethodHandler implements MethodHandler {

		Enhancer container;
		Map<MethodId, DelegateMethod> mapping;

		// メモリリークを防ぐためには、ここに置くしかないか・・・・
		InstanceContext perinstanceScope;

		public LatteMethodHandler(Enhancer container,
				Map<MethodId, DelegateMethod> mapping, Object owner) {
			this.container = container;
			this.mapping = mapping;
			this.perinstanceScope = new InstanceContext(owner);
		}

		public Object invoke(Object self, Method thisMethod, Method proceed,
				Object[] args) throws Throwable {
			ConcurrentHashMap<Object, Object> contextMap = NegroniContext.instanceScope
					.get();
			try {
				NegroniContext.instanceScope.set(perinstanceScope.getContext());
				return mapping.get(new MethodId(thisMethod)).invoke(container,
						self, thisMethod, args, perinstanceScope);
			} catch (RuntimeInvocationTargetException e) {
				Reflections.handleException(thisMethod, e);
				throw e;
			} finally {
				NegroniContext.instanceScope.set(contextMap);
			}
		}
	}

	static public class TargetMethodFilter implements MethodFilter {
		Set<Method> target;

		public TargetMethodFilter(Set<Method> target) {
			this.target = target;
		}

		public boolean isHandled(Method m) {
			return target.contains(m);
		}
	}

	@SuppressWarnings("unchecked")
	static public <T> T createProxy(Class<T> clazz, Enhancer container,
			Map<MethodId, DelegateMethod> mapping) {
		try {
			Class<? extends ProxyObject> proxyClass = getProxyFactory(clazz);
			ProxyObject po = container.getInstance(proxyClass);
			MethodHandler handler = new LatteMethodHandler(container, mapping,
					po);
			po.setHandler(handler);
			return (T) po;
		} catch (Exception e) {
			throw new NegroniException(e);
		}
	}
}
