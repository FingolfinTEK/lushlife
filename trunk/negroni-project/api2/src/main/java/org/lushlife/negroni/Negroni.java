package org.lushlife.negroni;

import java.util.Set;

import org.lushlife.negroni.impl.EnhancerImpl;
import org.lushlife.negroni.impl.SimpleContainer;
import org.lushlife.negroni.util.Reflections;

public class Negroni {

	static public Enhancer create() {
		return create(new SimpleContainer());
	}

	static public Enhancer create(Container container) {
		return new EnhancerImpl(container);
	}

	Set<Class<?>> mixinCLasses(Class<?> clazz) {
		return Reflections.mixinImplementClasses(clazz);
	}
}
