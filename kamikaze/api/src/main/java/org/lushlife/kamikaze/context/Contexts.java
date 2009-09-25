package org.lushlife.kamikaze.context;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.Contextual;

import org.lushlife.kamikaze.context.mock.MockSingletonContext;

public class Contexts {

	static private ThreadLocal<SingletonContext<?>> singletonContext = new ThreadLocal<SingletonContext<?>>();
	static private SingletonContext<?> mockContexet = new MockSingletonContext();
	static private ThreadLocal<Map<Contextual<? extends Object>, Object>> hiddenScope = new ThreadLocal<Map<Contextual<? extends Object>, Object>>() {
		@Override
		protected Map<Contextual<? extends Object>, Object> initialValue() {
			return new HashMap<Contextual<? extends Object>, Object>();
		}
	};

	public static Map<Contextual<? extends Object>, Object> getHiddenScope() {
		return hiddenScope.get();
	}

	public static SingletonContext<?> getSingletonContext() {
		SingletonContext<?> context = singletonContext.get();
		if (context == null) {
			// for unit test
			return mockContexet;
		}
		return context;
	}

	public static void setServletContext(SingletonContext<?> servletContext) {
		Contexts.singletonContext.set(servletContext);
	}

	public static <T> T get(Class<T> clazz) {
		return (T) getSingletonContext().get(clazz.getName());
	}

	public static <T> void setRegistries(Class<T> clazz, T instance) {
		getSingletonContext().set(clazz.getName(), instance);
	}
}
