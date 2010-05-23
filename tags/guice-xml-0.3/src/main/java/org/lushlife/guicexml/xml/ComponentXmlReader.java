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
package org.lushlife.guicexml.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.lushlife.guicexml.Component;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.guicexml.reflection.DependencyManagement;

/**
 * @author Takeshi Kondo
 */
@SuppressWarnings("unchecked")
public class ComponentXmlReader {

	public static Component<?> create(Element element,
			DependencyManagement xmlManagement) {
		assert element.getName().equals("component");
		Class<?> clazz = xmlManagement.toClass(element.attributeValue("class"));
		return create(element, xmlManagement, clazz);
	}

	private static Component<?> create(Element element,
			DependencyManagement xmlManagement, Class<?> clazz) {
		String name = element.attributeValue("name");
		if (name == null) {
			name = element.attributeValue("id");
		}
		boolean startup = Boolean.valueOf(element.attributeValue("startup"));

		Class<? extends Annotation> scope = xmlManagement.getScope(element
				.attributeValue("scope"));

		Type[] types = xmlManagement.toTypes(element.attributeValue("types"));
		Map<String, PropertyValue> attribute = new HashMap<String, PropertyValue>();

		for (Object obj : element.attributes()) {
			Attribute attr = (Attribute) obj;
			if (attr.getName().equals("name")) {
				continue;
			}
			if (attr.getName().equals("id")) {
				continue;
			}
			if (attr.getName().equals("class")) {
				continue;
			}
			if (attr.getName().equals("types")) {
				continue;
			}
			if (attr.getName().equals("scope")) {
				continue;
			}
			if (attr.getName().equals("startup")) {
				continue;
			}
			attribute.put(attr.getName(), xmlManagement.toPropertyValue(attr
					.getValue()));
		}
		for (Object obj : element.elements()) {
			Element ele = (Element) obj;
			if (ele.getName().equals("property")) {
				String stringValue = ele.attributeValue("value");
				String k = ele.attributeValue("name");
				PropertyValue v = (stringValue != null) ? xmlManagement
						.toPropertyValue(stringValue) : xmlManagement
						.toPropertyValue(ele);
				attribute.put(k, v);
			} else {
				String k = ele.getName();
				PropertyValue v = xmlManagement.toPropertyValue(ele);
				attribute.put(k, v);
			}
		}
		if (types == null || types.length == 0) {
			types = new Type[] { clazz };
		}
		return new Component(types, clazz, name, scope, startup, attribute);
	}

	public static Component create(String packageName, String className,
			Element element, DependencyManagement xmlManagement) {
		Class<?> clazz = xmlManagement.toClass(packageName, className);
		return create(element, xmlManagement, clazz);
	}

	public static Component create(Namespace namespace, String className,
			Element element, DependencyManagement xmlManagement) {
		Class<?> clazz = xmlManagement.toClass(namespace, className);
		return create(element, xmlManagement, clazz);
	}

}
