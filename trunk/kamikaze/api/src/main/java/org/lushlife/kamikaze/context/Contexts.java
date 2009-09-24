package org.lushlife.kamikaze.context;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.Contextual;

public class Contexts {

	static private ThreadLocal<SingletonContext<?>> servletContext = new ThreadLocal<SingletonContext<?>>();
	static private ThreadLocal<Map<Contextual<? extends Object>, Object>> hiddenScope = new ThreadLocal<Map<Contextual<? extends Object>, Object>>() {
		@Override
		protected Map<Contextual<? extends Object>, Object> initialValue() {
			return new HashMap<Contextual<? extends Object>, Object>();
		}
	};

	public static Map<Contextual<? extends Object>, Object> getHiddenScope() {
		return hiddenScope.get();
	}

	public static SingletonContext<?> getServletContext() {
		return servletContext.get();
	}

	public static void setServletContext(SingletonContext<?> servletContext) {
		Contexts.servletContext.set(servletContext);
	}

	public static <T> T get(Class<T> clazz) {
		return (T) servletContext.get().get(clazz.getName());
	}

	public static <T> void setRegistries(Class<T> clazz, T instance) {
		servletContext.get().set(clazz.getName(), instance);
	}
}
