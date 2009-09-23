package org.lushlife.negroni.core.impl.reflection;

import java.util.HashMap;
import java.util.Map;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.core.impl.EnhancerBase;
import org.lushlife.negroni.core.impl.delegate.DelegateMethod;


import javassist.util.proxy.ProxyObject;


public class ProxyFactory {

	static public <T> T createProxy(Object obj, Class<T> clazz,
			EnhancerBase container) {
		HashMap<MethodId, DelegateMethod> mapping = Reflections.createMapping(
				obj.getClass(), clazz, container.getConfigurator());
		return Javassists.createProxy(obj, clazz, container, mapping);
	}

	static public Class<?> getProxyFactory(Class<?> clazz) {
		return Javassists.getProxyFactory(clazz);
	}

	static public <T> T createProxy(Class<T> clazz, Enhancer container,
			Map<MethodId, DelegateMethod> mapping) {
		return Javassists.createProxy(clazz, container, mapping);
	}

}
