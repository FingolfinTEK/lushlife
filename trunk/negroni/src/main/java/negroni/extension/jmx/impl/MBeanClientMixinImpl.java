package negroni.extension.jmx.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;

import negroni.annotation.InstanceScoped;
import negroni.annotation.MethodMissing;
import negroni.annotation.Mixined;
import negroni.core.exceptions.NegroniException;
import negroni.core.impl.reflection.Reflections;
import negroni.extension.jmx.MBeanClientMixin;


@InstanceScoped
public class MBeanClientMixinImpl {

	HashMap<String, String> setters = new HashMap<String, String>();
	HashMap<String, String> getters = new HashMap<String, String>();

	@Mixined
	public void init(Object instance) throws IntrospectionException {
		BeanInfo info = Introspector.getBeanInfo(instance.getClass());
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			Method r = pd.getReadMethod();
			Method w = pd.getWriteMethod();
			Class<?> type = pd.getPropertyType();
			if (r != null) {
				if (type.equals(boolean.class) || type.equals(Boolean.class)) {
					getters.put(Reflections.toTypeId(r), r.getName().substring(
							2));
				} else {
					getters.put(Reflections.toTypeId(r), r.getName().substring(
							3));
				}
			}
			if (w != null) {
				setters.put(Reflections.toTypeId(w), w.getName().substring(3));
			}
		}
	}

	@Mixined
	@MethodMissing
	public Object methodMissing(MBeanClientMixin mbean, Method m,
			Object... args) throws InstanceNotFoundException,
			AttributeNotFoundException, InvalidAttributeValueException,
			MBeanException, ReflectionException, IOException {
		String typeId = Reflections.toTypeId(m);
		if (setters.containsKey(typeId)) {
			javax.management.Attribute at = new javax.management.Attribute(
					setters.get(typeId), args[0]);
			mbean.getServer().setAttribute(mbean.getObjectName(), at);
			return null;
		}

		if (getters.containsKey(typeId)) {
			return convert(mbean.getServer().getAttribute(
					mbean.getObjectName(), getters.get(typeId)), m
					.getReturnType());
		}

		return convert(invoke(mbean, m, args), m.getReturnType());
	}

	private Object convert(Object attribute, Class<?> returnType) {
		if (attribute instanceof CompositeData) {
			final CompositeData c = (CompositeData) attribute;
			try {
				Method m = returnType.getMethod("from", CompositeData.class);
				return Reflections.invoke(null, m, new Object[] { c });
			} catch (Exception e) {
				throw new NegroniException(e);
			}
		}
		return attribute;
	}

	private Object invoke(MBeanClientMixin mbean, Method m, Object... args)
			throws InstanceNotFoundException, MBeanException,
			ReflectionException, IOException {
		String[] sig = toSig(m.getParameterTypes());
		return mbean.getServer().invoke(mbean.getObjectName(), m.getName(),
				args, sig);
	}

	private String[] toSig(Class<?>[] parameterTypes) {
		String[] sigs = new String[parameterTypes.length];
		for (int i = 0; i < sigs.length; i++) {
			sigs[i] = parameterTypes[i].toString();
		}
		return sigs;
	}
}
