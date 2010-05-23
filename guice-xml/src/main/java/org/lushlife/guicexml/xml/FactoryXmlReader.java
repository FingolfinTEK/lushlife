package org.lushlife.guicexml.xml;

import java.lang.reflect.Type;
import java.util.List;

import org.dom4j.Element;
import org.lushlife.guicexml.Factory;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.guicexml.reflection.DependencyManagement;

public class FactoryXmlReader {

	@SuppressWarnings("unchecked")
	public static Factory create(Element element, DependencyManagement xmlManagement) {
		Type type = xmlManagement.toType(element.attributeValue("type"));
		String name = element.attributeValue("name");
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
		return new Factory(type, name, value);
	}
}
