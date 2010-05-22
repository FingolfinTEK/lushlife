package org.lushlife.guicexml;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.guicexml.xml.Injectable;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.JustInTimeProvider;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.name.Names;

public class Component<T> {
	class ComponentProvier implements Provider<T> {
		@Inject
		Injector injector;

		@Inject
		Expressions expressions;

		@Override
		public T get() {
			T t;
			try {
				t = injector.getInstance(internalKey);
				injectValues(t, expressions);
				postConstruct(t);
				return t;
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}

	}

	final protected Type[] types;
	final protected Class<T> clazz;
	final protected String name;
	final protected Class<? extends Annotation> scopeType;
	final protected boolean eagerSingleton;
	final protected Map<String, PropertyValue> attribute;
	final protected Map<String, Injectable> attribuiteInject = new HashMap<String, Injectable>();
	protected Method postConstruct;
	private Key<T> internalKey;

	public Component(Type[] bindTypes, Class<T> clazz, String name,
			Class<? extends Annotation> scopeType, boolean eagerSingleton,
			Map<String, PropertyValue> attribuite) {
		this.types = bindTypes;
		this.clazz = clazz;
		this.name = name;
		this.scopeType = scopeType;
		this.eagerSingleton = eagerSingleton;
		this.attribute = attribuite;
		initialize();
	}

	public String toString() {
		return "Component(name=\"" + name + "\" class=\"" + clazz + "\" types="
				+ Arrays.toString(types) + " scope=\""
				+ ((scopeType == null) ? "depend" : scopeType)
				+ "\" attribute=" + attribute + ")";
	}

	public void postConstruct(T t) {
		try {
			if (postConstruct != null) {
				int length = postConstruct.getParameterTypes().length;
				if (length == 1) {
					postConstruct.invoke(t, this);
					return;
				}
				if (length == 0) {
					postConstruct.invoke(t);
					return;
				}
				throw new IllegalStateException();
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	public void injectValues(T t, Expressions expressions) {
		for (Entry<String, PropertyValue> entry : attribute.entrySet()) {
			Injectable injectable = attribuiteInject.get(entry.getKey());
			if (injectable == null) {
				throw new IllegalArgumentException("error " + this + " key "
						+ entry.getKey());
			}
			Type type = injectable.getType();
			Object object = entry.getValue().resolveString(type, expressions);
			injectable.setValue(t, object);
		}
	}

	protected void initialize() {
		internalKey = Key.get(clazz, Names.named("_internal_"));
		initSetterInject();
		initFieldInject();
		this.postConstruct = findPostConstructMethod();
	}

	private Method findPostConstructMethod() {
		Class<?> clazz = this.clazz;
		while (clazz != null) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.isAnnotationPresent(PostConstruct.class)) {
					method.setAccessible(true);
					return method;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void bind(Binder binder) {
		binder.bind(internalKey).toProvider(new JustInTimeProvider<T>(clazz));
		for (Type type : types) {
			Key<T> key = (Key<T>) ((name != null) ? Key.get(type, Names
					.named(name)) : Key.get(type));
			ScopedBindingBuilder provider = binder.bind(key).toProvider(
					new ComponentProvier());
			if (eagerSingleton) {
				provider.asEagerSingleton();
			} else if (scopeType != null) {
				provider.in(scopeType);
			}
		}
	}

	protected void initFieldInject() {
		Class<?> clazz = this.clazz;
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (!attribuiteInject.containsKey(field.getName())) {
					attribuiteInject.put(field.getName(),
							new Injectable.FieldInject(field));
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

	protected void initSetterInject() {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			for (PropertyDescriptor desc : beanInfo.getPropertyDescriptors()) {
				Method writeMethod = desc.getWriteMethod();
				if (writeMethod != null) {
					attribuiteInject.put(desc.getName(),
							new Injectable.MethodInject(writeMethod));
				}
			}
		} catch (IntrospectionException e) {
			throw new IllegalStateException(e);
		}
	}

}
