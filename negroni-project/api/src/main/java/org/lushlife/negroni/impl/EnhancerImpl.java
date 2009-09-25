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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javassist.util.proxy.ProxyFactory;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.LogMsgNGLN;
import org.lushlife.negroni.delegate.DelegateMethod;
import org.lushlife.negroni.delegate.DelegateMethodFactory;
import org.lushlife.negroni.util.DelegateMethodHandler;
import org.lushlife.negroni.util.Reflections;
import org.lushlife.negroni.util.TargetMethodFilter;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

/**
 * @author Takeshi Kondo
 */
public class EnhancerImpl implements Enhancer {

	static private Log log = Logging.getLog(EnhancerImpl.class);
	private Container container;
	private Map<Class<?>, Class<?>> cache = new ConcurrentHashMap<Class<?>, Class<?>>();
	private Lock lock = new ReentrantLock();

	public EnhancerImpl(Container container) {
		super();
		this.container = container;
	}

	protected <T> Class<? extends T> enhace(Class<T> clazz, Container container) {
		Class<? extends T> enhahcedClass = (Class<? extends T>) cache
				.get(clazz);
		if (enhahcedClass == null) {
			lock.lock();
			try {
				enhahcedClass = (Class<? extends T>) cache.get(clazz);
				if (enhahcedClass == null) {
					Map<Method, DelegateMethod> methodMapping = new HashMap<Method, DelegateMethod>();
					Set<Method> undifinedMethods = Reflections
							.undifinedMethods(clazz);
					Set<Class<?>> mixinImplementClasses = Reflections
							.mixinImplementClasses(clazz);
					for (Method method : undifinedMethods) {
						List<DelegateMethod> delegateMethods = new ArrayList<DelegateMethod>();
						delegateMethods.addAll(findDelegateMethods(clazz,
								clazz, method));

						for (Class<?> mixinClass : mixinImplementClasses) {
							log.log(LogMsgNGLN.NGLN00003, clazz, mixinClass);
							delegateMethods.addAll(findDelegateMethods(clazz,
									mixinClass, method));
						}
						if (delegateMethods.size() == 0) {
							throw new NullPointerException(Logging.getMessage(
									LogMsgNGLN.NGLN00001, method));
						}
						DelegateMethod delegateMethod = select(delegateMethods);
						methodMapping.put(method, delegateMethod);
					}

					ProxyFactory factory = new ProxyFactory();
					if (clazz.isInterface()) {
						factory.setInterfaces(new Class[] { clazz });
					} else {
						factory.setSuperclass(clazz);
					}
					factory.setFilter(new TargetMethodFilter(methodMapping
							.keySet()));
					factory.setHandler(new DelegateMethodHandler(container,
							methodMapping));
					enhahcedClass = factory.createClass();
					cache.put(clazz, enhahcedClass);
				}
			} finally {
				lock.unlock();
			}
		}
		return enhahcedClass;
	}

	private DelegateMethod select(List<DelegateMethod> delegateMethods) {
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
				boolean isAccept = delegateMethod.isAccept(ownerClass, method);
				log.log(LogMsgNGLN.NGLN00002, delegateMethod
						.getDelegateMethod(), method, isAccept);
				if (isAccept) {
					delegateMethods.add(delegateMethod);
				}
			}
		}
		return delegateMethods;
	}

	public <T> Class<? extends T> enhace(Class<T> clazz) {
		return enhace(clazz, container);
	}
}
