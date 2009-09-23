package org.lushlife.negroni.core.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Identifier;
import org.lushlife.negroni.Mix;
import org.lushlife.negroni.core.closure.Closure;
import org.lushlife.negroni.core.closure.ClosureHelper;
import org.lushlife.negroni.core.exceptions.NegroniException;
import org.lushlife.negroni.core.impl.reflection.ProxyFactory;
import org.lushlife.negroni.core.impl.reflection.Reflections;



public abstract class EnhancerBase implements Enhancer {
	ConfiguratorBase configurator;

	public EnhancerBase(ConfiguratorBase configurator) {
		this.configurator = configurator;
	}

	private Map<Class<?>, Map<String, Method>> closureMethodCashe = new ConcurrentHashMap<Class<?>, Map<String, Method>>();

	public <T> Object call(final Mix<T> instance) {
		Class<?> type = instance.getActualType();
		Object t;
		if (type != null) {
			t = mixin(instance);
		} else {
			t = instance.getObj();
			type = t.getClass();
		}
		Map<String, Method> closureMethods = getClosure(type);
		final Method m = getClosureMethod(instance.getClass(), closureMethods);
		m.setAccessible(true);
		ClosureHelper helper = ClosureHelper.get(m);
		Closure<?> c = helper.toDelegator(instance, m);
		Method m2 = closureMethods.get(m.getName());
		Object[] args = instance.getArgs();
		Object[] tmp = new Object[args.length + 1];
		System.arraycopy(args, 0, tmp, 0, args.length);
		tmp[tmp.length - 1] = c;
		return Reflections.invoke(t, m2, tmp);
	}

	private Map<String, Method> getClosure(Class<?> type) {
		if (closureMethodCashe.containsKey(type)) {
			return closureMethodCashe.get(type);
		}
		Map<String, Method> mapping = new HashMap<String, Method>();
		for (Method method : Reflections.allMethod(type)) {
			Class<?>[] param = method.getParameterTypes();
			if (param.length == 0) {
				continue;
			}
			if (Closure.class.isAssignableFrom(param[param.length - 1])) {
				mapping.put(method.getName(), method);
			}
		}
		closureMethodCashe.put(type, mapping);
		return mapping;
	}

	private Method getClosureMethod(Class<? extends Mix> clazz,
			Map<String, Method> closures) {
		for (Method m : clazz.getDeclaredMethods()) {
			if (closures.containsKey(m.getName())) {
				return m;
			}
		}
		throw new NegroniException("closure method not found " + clazz);
	}

	public <T> T mixin(final Mix<T> mixin) {
		Class<T> type = mixin.getActualType();
		return ProxyFactory.createProxy(mixin.getObj(), type, this);
	}

	public Object getInstance(String name) {
		return getInstance((Identifier) getConfigurator().getNameMapping().get(
				name));
	}

	public <T> T getInstance(Class<T> clazz, Annotation bindingType) {
		return (T) getInstance(getConfigurator().toId(clazz, bindingType));
	}

	public ConfiguratorBase getConfigurator() {
		return configurator;
	}

}
