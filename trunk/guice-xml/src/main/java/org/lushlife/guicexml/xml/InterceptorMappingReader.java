package org.lushlife.guicexml.xml;

import java.util.regex.Pattern;

import org.dom4j.Element;
import org.lushlife.guicexml.InterceptorMapping;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.guicexml.reflection.DependencyManagement;

public class InterceptorMappingReader {
	public static InterceptorMapping create(Element element,
			DependencyManagement xmlManagement) {
		assert element.getName().equals("interceptor-mapping");

		PropertyValue[] interceptors = xmlManagement.toListPropertyValue(
				element.attributeValue("interceptors")).getValues();
		Pattern targetClass = (Pattern) xmlManagement.toPropertyValue(
				element.attributeValue("target-class")).resolveString(
				Pattern.class, null);
		Pattern excludeClass = (Pattern) xmlManagement.toPropertyValue(
				element.attributeValue("exclude-class")).resolveString(
				Pattern.class, null);
		Pattern targetMethod = (Pattern) xmlManagement.toPropertyValue(
				element.attributeValue("target-method")).resolveString(
				Pattern.class, null);
		Pattern excludeMethod = (Pattern) xmlManagement.toPropertyValue(
				element.attributeValue("exclude-method")).resolveString(
				Pattern.class, null);

		return new InterceptorMapping(interceptors, targetClass, excludeClass,
				targetMethod, excludeMethod);

	}

}
