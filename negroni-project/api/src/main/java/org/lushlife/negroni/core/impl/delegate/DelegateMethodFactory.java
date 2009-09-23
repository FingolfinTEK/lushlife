package org.lushlife.negroni.core.impl.delegate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lushlife.negroni.Configurator;
import org.lushlife.negroni.Identifier;
import org.lushlife.negroni.annotation.MethodMissing;
import org.lushlife.negroni.annotation.Mixined;
import org.lushlife.negroni.core.impl.ConfiguratorBase;
import org.lushlife.negroni.core.impl.reflection.Reflections;



public class DelegateMethodFactory {

	static public List<DelegateMethod> getDelegateMethods(Class<?> interfaces,
			ConfiguratorBase initializer) {
		if (initializer.getCash().containsKey(interfaces)) {
			return initializer.getCash().get(interfaces);
		}

		Set<Class<?>> mixinClasses = Reflections
				.mixinImplementClasses(interfaces);
		List<DelegateMethod> delegateMethods = new ArrayList<DelegateMethod>();

		Map<Method, Identifier<?>> mixinObjectMapping = createMixinMapping(
				mixinClasses, initializer);

		createDelgate(mixinObjectMapping, delegateMethods, interfaces);

		if (mixinClasses != null) {
			for (Class<?> clazz : mixinClasses) {
				createDelgate(mixinObjectMapping, delegateMethods, clazz);
			}
		}
		return delegateMethods;

	}

	@SuppressWarnings("unchecked")
	static private Map<Method, Identifier<?>> createMixinMapping(
			Collection<Class<?>> mixinClasses, Configurator creator) {
		HashMap<Method, Identifier<?>> mapping = new HashMap<Method, Identifier<?>>();
		if (mixinClasses != null) {
			for (Class<?> c : mixinClasses) {
				for (Method m : Reflections.getMethodsPresentByAnnotations(c,
						Mixined.class)) {
					mapping.put(m, creator.toId(c));
				}
			}
		}
		return mapping;
	}

	private static void createDelgate(
			Map<Method, Identifier<?>> mixinObjectMapping,
			List<DelegateMethod> delegateMethods, Class<?> clazz) {
		for (Method m : clazz.getMethods()) {
			DelegateMethod delegateMethod = toDelegateMethod(m,
					mixinObjectMapping.get(m));
			if (delegateMethod != null) {
				delegateMethods.add(delegateMethod);
			}
		}
	}

	static public DelegateMethod toDelegateMethod(Method m, Identifier<?> id) {
		boolean isMethodMissing = m.isAnnotationPresent(MethodMissing.class);
		boolean isMixin = m
				.isAnnotationPresent(org.lushlife.negroni.annotation.Mixined.class);
		boolean isVarArgs = m.isVarArgs();

		if (isMethodMissing && isMixin) {
			if (isVarArgs) {
				return new MixinMethodMissingVarArgsMethodDeleage(m, id);
			} else {
				return new MixinMethodMissingMethodDelegate(m, id);
			}
		}
		if (isMethodMissing) {
			if (isVarArgs) {
				return new MethodMissingVarArgsMethodDelegate(m);
			} else {
				return new MethodMissingMethodDelegate(m);
			}
		}
		if (isMixin) {
			if (isVarArgs) {
				return new MixinVarArgsMethodDelegate(m, id);
			} else {
				return new MixinMethodDelegate(m, id);
			}
		}
		return null;
	}

}
