package org.lushlife.guicexml.xml;

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
import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.property.ExpressionPropertyValue;
import org.lushlife.guicexml.property.ListPropertyValue;
import org.lushlife.guicexml.property.MapPropertyValue;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.guicexml.property.SimplePropertyValue;
import org.lushlife.guicexml.reflection.ParameterizedTypeImpl;
import org.lushlife.guicexml.spi.ScopeBinding;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

public class XmlManagement {

	private Map<String, Class<? extends Annotation>> scopes = new HashMap<String, Class<? extends Annotation>>();

	public XmlManagement() {
		initialize();
	}

	private void initialize() {
		bindScope(new ScopeBinding() {

			@Override
			public Class<? extends Annotation> getScopeAnnotation() {
				return Singleton.class;
			}

			@Override
			public String[] getNames() {
				return new String[] { "singleton", "application" };
			}

		});
		ServiceLoader<ScopeBinding> loadScopes = ServiceLoader
				.load(ScopeBinding.class);
		for (ScopeBinding binding : loadScopes) {
			bindScope(binding);
		}
	}

	private void bindScope(ScopeBinding scopeBinding) {
		for (String name : scopeBinding.getNames()) {
			scopes.put(name, scopeBinding.getScopeAnnotation());
		}
	}

	public Class<? extends Annotation> getScope(String name) {
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
	private PropertyValue toMapPropertyValue(Element value) {
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

	private PropertyValue toListPropertyValue(Element value) {
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

}
