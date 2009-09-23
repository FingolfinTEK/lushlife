package org.lushlife.negroni.annotation.literal;

import java.util.ArrayList;
import java.util.List;

import org.lushlife.negroni.Configurator;
import org.lushlife.negroni.annotation.Config;
import org.lushlife.negroni.core.impl.inject.InjectionUnit;
import org.lushlife.negroni.core.impl.inject.Injections;
import org.lushlife.negroni.core.impl.reflection.Reflections;



public class Configuration extends AnnotationLiteral<Config>
		implements Config {
	final List<InjectionUnit<?>> injectionUnits;

	public List<InjectionUnit<?>> getInjectionUnits() {
		return injectionUnits;
	}

	public Configuration(Enum<?> e, Configurator initializer) {
		this.bind = Reflections.getEnumAnnotation(e, Config.class);
		injectionUnits = Injections.toInjectionUnits(type(), e, initializer);
	}

	public Configuration(Class<?> clazz, Configurator guiceInitializer) {
		this.bind = new SimpleConfigAnnotationLiteral(clazz, false);
		this.injectionUnits = new ArrayList<InjectionUnit<?>>();
	}

	final Config bind;

	public boolean named() {
		return bind.named();
	}

	public Class<?> type() {
		return bind.type();
	}

}
