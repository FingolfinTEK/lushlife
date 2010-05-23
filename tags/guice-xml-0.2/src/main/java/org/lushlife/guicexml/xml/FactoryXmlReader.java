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
import java.util.List;

import org.dom4j.Element;
import org.lushlife.guicexml.Factory;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.guicexml.reflection.DependencyManagement;

/**
 * @author Takeshi Kondo
 */
public class FactoryXmlReader {

	@SuppressWarnings("unchecked")
	public static Factory create(Element element,
			DependencyManagement xmlManagement) {
		assert element.getName().equals("factory");

		Type type = xmlManagement.toType(element.attributeValue("type"));
		String name = element.attributeValue("name");
		Class<? extends Annotation> scope = xmlManagement.getScope(element
				.attributeValue("scope"));
		boolean startup = Boolean.valueOf(element.attributeValue("startup"));
		PropertyValue value;
		List list = element.elements();
		if (list.size() == 0) {
			String attr = element.attributeValue("value");
			if (attr != null) {
				value = xmlManagement.toPropertyValue(attr);
			} else {
				value = xmlManagement.toPropertyValue(element.getTextTrim());
			}
		} else {
			value = xmlManagement.toPropertyValue(element);
		}
		return new Factory(type, name, value, scope, startup);
	}
}
