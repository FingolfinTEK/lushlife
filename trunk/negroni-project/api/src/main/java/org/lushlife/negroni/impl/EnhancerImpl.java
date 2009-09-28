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
package org.lushlife.negroni.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.LogMsgNGLN;
import org.lushlife.negroni.delegate.DelegateMethod;
import org.lushlife.negroni.delegate.DelegateMethodFactory;
import org.lushlife.negroni.util.AbstractMethodFilter;
import org.lushlife.negroni.util.DelegateMethodHandler;
import org.lushlife.negroni.util.Javassist;
import org.lushlife.negroni.util.Reflections;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

/**
 * @author Takeshi Kondo
 */
public class EnhancerImpl implements Enhancer {

	static private Log log = Logging.getLog(EnhancerImpl.class);
	private Container container;

	public EnhancerImpl(Container container) {
		super();
		this.container = container;
	}

	protected <T> Class<? extends T> enhace(Class<T> clazz, Container container) {
		Map<Method, DelegateMethod> methodMapping = createDelegateMethodMapping(
				clazz, clazz);
		return createEnhancedClass(clazz, container, methodMapping);
	}

	private <T> Class<? extends T> createEnhancedClass(Class<T> clazz,
			Container container, Map<Method, DelegateMethod> methodMapping) {
		try {
			return Javassist.createEnhancedClass(clazz, container,
					methodMapping, Thread.currentThread()
							.getContextClassLoader(), null);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private <T> Map<Method, DelegateMethod> createDelegateMethodMapping(
			Class<?> ownerClass, Class<T> mixinClass) {
		Map<Method, DelegateMethod> methodMapping = new HashMap<Method, DelegateMethod>();
		Set<Method> undifinedMethods = Reflections.undifinedMethods(mixinClass);
		Set<Class<?>> mixinImplementClasses = Reflections
				.mixinImplementClasses(mixinClass);
		for (Method method : undifinedMethods) {
			List<DelegateMethod> delegateMethods = findDelegateMethods(
					ownerClass, mixinImplementClasses, method);
			if (delegateMethods.size() == 0) {
				throw new IllegalArgumentException(Logging.getMessage(
						LogMsgNGLN.NGLN00001, method, ownerClass));
			}
			DelegateMethod delegateMethod = selectMaxPrecidenceDelegateMethod(delegateMethods);
			methodMapping.put(method, delegateMethod);
		}
		return methodMapping;
	}

	private <T> List<DelegateMethod> findDelegateMethods(Class<T> ownerClass,
			Set<Class<?>> mixinImplementClasses, Method method) {
		List<DelegateMethod> delegateMethods = new ArrayList<DelegateMethod>();
		delegateMethods.addAll(findDelegateMethods(ownerClass, ownerClass,
				method));

		for (Class<?> mixinClass : mixinImplementClasses) {
			log.log(LogMsgNGLN.NGLN00003, ownerClass, mixinClass);
			delegateMethods.addAll(findDelegateMethods(ownerClass, mixinClass,
					method));
		}
		return delegateMethods;
	}

	private DelegateMethod selectMaxPrecidenceDelegateMethod(
			List<DelegateMethod> delegateMethods) {
		Collections.sort(delegateMethods);
		return delegateMethods.get(0);
	}

	private List<DelegateMethod> findDelegateMethods(Class<?> ownerClass,
			Class<?> mixinClass, Method method) {
		List<DelegateMethod> delegateMethods = new ArrayList<DelegateMethod>();
		for (Method mixinMethod : mixinClass.getMethods()) {
			DelegateMethod delegateMethod = DelegateMethodFactory
					.toDelegateMethod(mixinMethod, mixinClass);
			if (delegateMethod != null) {
				boolean accept = delegateMethod.isAccept(ownerClass, method);
				log.log(LogMsgNGLN.NGLN00002, delegateMethod
						.getDelegateMethod(), method, accept);
				if (accept) {
					delegateMethods.add(delegateMethod);
				}
			}
		}
		return delegateMethods;
	}

	public <T> Class<? extends T> enhace(Class<T> clazz) {
		return enhace(clazz, container);
	}

	protected <T> T wrap(Object instance, Class<T> mixinInterface,
			Container container) {
		if (!mixinInterface.isInterface()) {
			throw new IllegalStateException("mixinInterface is not interface. "
					+ mixinInterface);
		}
		Map<Method, DelegateMethod> mapping = createDelegateMethodMapping(
				instance.getClass(), mixinInterface);

		ProxyFactory factory = new ProxyFactory();
		factory.setInterfaces(new Class[] { mixinInterface });
		factory.setFilter(new AbstractMethodFilter());
		Class<? extends ProxyObject> proxyClass = factory.createClass();
		ProxyObject po;
		try {
			po = proxyClass.newInstance();
			DelegateMethodHandler handler = new DelegateMethodHandler(instance,
					container, mapping);
			po.setHandler(handler);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return (T) po;
	}

	public <T> T mixin(Class<T> mixinInterface, Object instance) {
		return wrap(instance, mixinInterface, container);
	}
}
