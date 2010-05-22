package org.lushlife.guicexml.xml;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.lushlife.guicexml.Component;
import org.lushlife.guicexml.property.PropertyValue;

public class ComponentXmlReader {

	@SuppressWarnings("unchecked")
	public static Component<?> create(Element element,
			XmlManagement xmlManagement) {
		assert element.getName().equals("component");

		String name = element.attributeValue("name");
		Class<?> clazz = xmlManagement.toClass(element.attributeValue("class"));
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

	public static void main(String[] args) throws DocumentException {
		InputStream resource = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("components.xml");
		System.out.println(resource);
		Element rootElement = XML.getRootElement(resource);
		for (Object obj : rootElement.elements()) {
			Element element = (Element) obj;
			if (element.getName().equals("component")) {
				System.out.println(ComponentXmlReader.create(element,
						new XmlManagement()));
			}
			System.out.println(element.asXML());
		}
	}
}
