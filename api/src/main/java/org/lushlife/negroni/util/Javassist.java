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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.delegate.DelegateMethod;

/**
 * @author Takeshi Kondo
 */
public class Javassist {
	static final AtomicInteger counter = new AtomicInteger();
	static final WeakHashMap<ClassLoader, ClassPool> pools = new WeakHashMap<ClassLoader, ClassPool>();
	private static final String CONTEXT_MAP_FIELD = "__$contextMap";

	synchronized static public <T> Class<? extends T> createEnhancedClass(
			Class<T> clazz, Container container,
			Map<Method, DelegateMethod> mapping, ClassLoader loader,
			ProtectionDomain domain) throws Exception {

		ClassPool classPool = getClassPool(loader);
		CtClass superClass = classPool.get(clazz.getName());
		CtClass ctClass = createCtClass(clazz, classPool, superClass);
		addClassField(ctClass);

		Map<String, Method> methodMapping = new HashMap<String, Method>();
		Map<String, DelegateMethod> delegateMethodMapping = new HashMap<String, DelegateMethod>();
		addMethodAndStaticField(clazz, mapping, loader, domain, superClass,
				ctClass, methodMapping, delegateMethodMapping);
		Class<? extends T> enhancedClass = ctClass.toClass(loader, domain);
		setStaticFields(container, methodMapping, delegateMethodMapping,
				enhancedClass);

		return enhancedClass;

	}

	private static ClassPool getClassPool(ClassLoader loader) {
		ClassPool classPool = pools.get(loader);
		if (classPool == null) {
			classPool = new ClassPool();
			classPool.appendClassPath(new LoaderClassPath(loader));
			pools.put(loader, classPool);
		}
		return classPool;
	}

	private static <T> int addMethodAndStaticField(Class<T> clazz,
			Map<Method, DelegateMethod> mapping, ClassLoader loader,
			ProtectionDomain domain, CtClass superClass, CtClass ctClass,
			Map<String, Method> methodMapping,
			Map<String, DelegateMethod> delegateMethodMapping)
			throws NoSuchMethodException, CannotCompileException,
			ClassNotFoundException, NotFoundException {
		int counter = 0;
		for (CtMethod method : superClass.getMethods()) {
			if (Modifier.isAbstract(method.getModifiers())) {
				Method originalMethod = clazz.getMethod(method.getName(),
						toClasses(method.getParameterTypes(), loader, domain));
				String methodField = "__$method" + counter;
				String delegateMethodField = "__$delegateMethod" + counter;
				methodMapping.put(methodField, originalMethod);
				delegateMethodMapping.put(delegateMethodField, mapping
						.get(originalMethod));

				ctClass.addField(CtField.make("static public "
						+ DelegateMethod.class.getName() + " "
						+ delegateMethodField + ";", ctClass));

				ctClass.addField(CtField.make("static public "
						+ Method.class.getName() + " " + methodField + ";",
						ctClass));

				CtMethod createMethod = new CtMethod(method.getReturnType(),
						method.getName(), method.getParameterTypes(), ctClass);
				String returnSource;
				if (createMethod.getReturnType().getName().equals("void")) {
					returnSource = "";
				} else {
					returnSource = "return ("
							+ createMethod.getReturnType().getName() + ")";
				}
				createMethod.setBody(returnSource + delegateMethodField
						+ ".invoke(" + CONTEXT_MAP_FIELD + ","
						+ CONTAINER_FIELD_NAME + ",this," + methodField + ","
						+ "$args" + ");");

				ctClass.addMethod(createMethod);
				counter++;
			}
		}
		return counter;
	}

	private static <T> CtClass createCtClass(Class<T> clazz,
			ClassPool classPool, CtClass superClass)
			throws CannotCompileException, NotFoundException {
		CtClass ctClass;
		String className = clazz.getName() + "_negloni$$"
				+ counter.getAndIncrement();
		if (clazz.isInterface()) {
			ctClass = classPool.makeClass(className);
			ctClass.setInterfaces(new CtClass[] { superClass });
		} else {
			ctClass = classPool.makeClass(className, superClass);
		}
		ctClass.setModifiers(Modifier.PUBLIC);
		for (CtConstructor constructor : superClass.getConstructors()) {
			CtConstructor ctConstructor = new CtConstructor(constructor
					.getParameterTypes(), ctClass);
			ctConstructor.setExceptionTypes(constructor.getExceptionTypes());
			AttributeInfo annottion = constructor.getMethodInfo().getAttribute(
					AnnotationsAttribute.visibleTag);
			if (annottion != null) {
				ctConstructor.getMethodInfo().addAttribute(annottion);
			}
			ctConstructor.setBody("super($$);");
			ctConstructor.setModifiers(Modifier.PUBLIC);
			ctClass.addConstructor(ctConstructor);
		}

		if (ctClass.getConstructors().length == 0) {
			CtConstructor constructor = CtNewConstructor
					.defaultConstructor(ctClass);
			ctClass.addConstructor(constructor);
		}
		return ctClass;
	}

	static String CONTAINER_FIELD_NAME = "__$container";

	private static void addClassField(CtClass ctClass)
			throws CannotCompileException {
		String source = "static public " + Container.class.getName() + " "
				+ CONTAINER_FIELD_NAME + ";";
		ctClass.addField(CtField.make(source, ctClass));
		// インスタンスごとのスコープ
		ctClass.addField(CtField.make("private java.util.Map "
				+ CONTEXT_MAP_FIELD + " = new "
				+ ConcurrentHashMap.class.getName() + "();", ctClass));
	}

	private static <T> void setStaticFields(Container container,
			Map<String, Method> methodMapping,
			Map<String, DelegateMethod> delegateMethodMapping,
			Class<? extends T> enhancedClass) throws NoSuchFieldException,
			IllegalAccessException {
		Field field = enhancedClass.getField(CONTAINER_FIELD_NAME);
		field.set(null, container);

		for (String fieldName : methodMapping.keySet()) {
			Field methodField = enhancedClass.getField(fieldName);
			methodField.set(null, methodMapping.get(fieldName));
		}

		for (String fieldName : delegateMethodMapping.keySet()) {
			Field methodField = enhancedClass.getField(fieldName);
			methodField.set(null, delegateMethodMapping.get(fieldName));
		}
	}

	private static Class[] toClasses(CtClass[] parameterTypes,
			ClassLoader loader, ProtectionDomain domain)
			throws CannotCompileException, ClassNotFoundException,
			NotFoundException {
		Class<?>[] classes = new Class<?>[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			classes[i] = loadClass(loader, parameterTypes[i]);
		}
		return classes;

	}

	private static Class<?> loadClass(ClassLoader loader, CtClass ctClass)
			throws ClassNotFoundException, NotFoundException {
		if (ctClass.isArray()) {
			Class<?> componentType = loadClass(loader, ctClass
					.getComponentType());
			return Array.newInstance(componentType, 0).getClass();
		} else {
			return loader.loadClass(ctClass.getName());
		}
	}

}
