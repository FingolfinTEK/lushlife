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
