/*
 * Copyright 2010 the original author or authors.
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
package org.lushlife.guicexml.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.el.ValueExpression;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.property.ExpressionPropertyValue;
import org.lushlife.guicexml.property.ListPropertyValue;
import org.lushlife.guicexml.property.MapPropertyValue;
import org.lushlife.guicexml.property.NullListPropertyValue;
import org.lushlife.guicexml.property.NullPropertyValue;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.guicexml.property.SimplePropertyValue;
import org.lushlife.guicexml.spi.NameSpaceBinding;
import org.lushlife.guicexml.spi.ScopeBinding;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

/**
 * @author Takeshi Kondo
 */
public class DependencyManagement {

	private Map<String, Class<? extends Annotation>> scopes = new HashMap<String, Class<? extends Annotation>>();
	private Map<String, String> namespace = new HashMap<String, String>();

	public DependencyManagement() {
		initialize();
	}

	protected void initialize() {
		initializeScope();
		initializeNamepase();

	}

	private void initializeNamepase() {
		ServiceLoader<NameSpaceBinding> ns = ServiceLoader
				.load(NameSpaceBinding.class);
		for (NameSpaceBinding binding : ns) {
			bindNamespace(binding);
		}
	}

	private void initializeScope() {
		bindScope(new ScopeBinding() {

			@Override
			protected void configuire() {
				bindScope(Singleton.class).toName("singleton");
				bindScope(Singleton.class).toName("application");

			}
		});
		ServiceLoader<ScopeBinding> loadScopes = ServiceLoader
				.load(ScopeBinding.class);
		for (ScopeBinding binding : loadScopes) {
			bindScope(binding);
		}
	}

	private void bindNamespace(NameSpaceBinding binding) {
		binding.configure(namespace);
	}

	private void bindScope(ScopeBinding scopeBinding) {
		scopeBinding.configure(scopes);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Annotation> getScope(String name) {
		if (name == null) {
			return null;
		}
		if (name.startsWith("@")) {
			return (Class<? extends Annotation>) toClass(name.substring(1));
		}
		return scopes.get(name);
	}

	public Type[] toTypes(String type) {
		if (type == null) {
			return null;
		}
		String[] values = type.split(",");
		Type[] types = new Type[values.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = toType(values[i]);
		}
		return types;
	}

	public Type toType(String type) {
		if (type == null) {
			return null;
		}
		if (type.contains("<")) {
			return parseGenericType(type);
		}
		if (type.contains("[")) {
			return parseArrayType(type);
		}
		return toClass(type);

	}

	private Type parseArrayType(String type) {
		char[] value = type.toCharArray();
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		int demension = 0;
		while (i < value.length) {
			if (value[i] == '[') {
				demension++;
				i += 2;
				continue;
			}
			sb.append(value[i]);
			i++;
		}
		return toClass(sb.toString(), demension);
	}

	public Type parseGenericType(final String type) {
		char[] value = type.toCharArray();
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < value.length) {
			if (value[i] == '<') {
				final Type[] types = toTypes(new String(value, i + 1,
						value.length - 2 - i));
				return new ParameterizedTypeImpl(toClass(sb.toString()), types);
			}
			sb.append(value[i]);
			i++;
		}
		throw new IllegalArgumentException();
	}

	private Class<?> toClass(String clazzName, int arraydemention) {
		Class<?> clazz = toClass(clazzName);
		while (arraydemention != 0) {
			clazz = Array.newInstance(clazz, 0).getClass();
			arraydemention--;
		}
		return clazz;
	}

	public Class<?> toClass(String clazzName) {
		try {
			return Class.forName(clazzName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}

	}

	public PropertyValue toPropertyValue(String value) {
		if (value == null) {
			return new NullPropertyValue(this);
		}
		if (value.startsWith("#{")) {
			ValueExpression valueExpression = Expressions
					.createValueExpression(value);
			return new ExpressionPropertyValue(valueExpression);
		} else if (value.contains(",")) {
			String[] values = value.split(",");
			PropertyValue[] propertyValues = new PropertyValue[values.length];
			for (int i = 0; i < propertyValues.length; i++) {
				propertyValues[i] = toPropertyValue(values[i]);
			}
			return new ListPropertyValue(propertyValues);
		} else {
			return new SimplePropertyValue(value);
		}
	}

	@SuppressWarnings("unchecked")
	public PropertyValue toPropertyValue(Element value) {
		List list = value.elements();
		if (list.size() == 0) {
			return toPropertyValue(value.getTextTrim());
		}
		Element element = (Element) list.get(0);
		if (element.getName().equals("value")) {
			return toListPropertyValue(value);
		}
		if (element.getName().equals("key")) {
			return toMapPropertyValue(value);
		}
		// TODO message
		throw new IllegalArgumentException(""
				+ value.asXML().replace("\r", " "));
	}

	@SuppressWarnings("unchecked")
	public MapPropertyValue toMapPropertyValue(Element value) {
		List list = value.elements();
		Map<PropertyValue, PropertyValue> map = Maps.newLinkedHashMap();
		for (int i = 0; i < list.size(); i += 2) {
			Element k = (Element) list.get(i);
			Element v = (Element) list.get(i + 1);
			if (!k.getName().equals("key")) {
				// TODO message
				throw new IllegalArgumentException(""
						+ value.asXML().replace("\n", " "));
			}
			if (!v.getName().equals("value")) {
				// TODO message
				throw new IllegalArgumentException(""
						+ value.asXML().replace("\n", " "));
			}
			map.put(toPropertyValue(k), toPropertyValue(v));
		}
		return new MapPropertyValue(map);
	}

	public ListPropertyValue toListPropertyValue(String value) {
		if (value == null) {
			return new NullListPropertyValue(this);
		}
		String[] values = value.split(",");
		PropertyValue[] propertyValues = new PropertyValue[values.length];
		for (int i = 0; i < values.length; i++) {
			propertyValues[i] = toPropertyValue(values[i]);
		}
		return new ListPropertyValue(propertyValues);
	}

	public ListPropertyValue toListPropertyValue(Element value) {
		List<PropertyValue> list = new ArrayList<PropertyValue>();
		for (Object element : value.elements()) {
			Element el = (Element) element;
			if (!el.getName().equals("value")) {
				// TODO message
				throw new IllegalArgumentException("" + value.asXML());
			}
			list.add(toPropertyValue(el));
		}
		return new ListPropertyValue(list.toArray(new PropertyValue[0]));
	}

	public Class<?> toClass(String packageName, String className) {
		return toClass(packageName + "." + toChamelCase(className));
	}

	public Class<?> toClass(Namespace namespace, String className) {
		String packageName = this.namespace.get(namespace.getText());
		if (packageName == null) {
			throw new IllegalArgumentException("don't bind namespace "
					+ namespace);
		}
		return toClass(packageName, className);
	}

	public String toChamelCase(String clazzName) {
		char[] value = clazzName.toCharArray();
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toUpperCase(value[0]));
		for (int i = 1; i < value.length; i++) {
			if (value[i] == '-') {
				if (i + 1 >= value.length) {
					throw new IllegalArgumentException("illegal name "
							+ clazzName);
				}
				sb.append(Character.toUpperCase(value[i + 1]));
				i++;
				continue;
			}
			sb.append(value[i]);
		}
		return sb.toString();
	}

}
