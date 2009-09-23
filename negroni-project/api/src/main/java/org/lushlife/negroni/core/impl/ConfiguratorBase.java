package org.lushlife.negroni.core.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lushlife.negroni.Configurator;
import org.lushlife.negroni.Identifier;
import org.lushlife.negroni.annotation.literal.Configuration;
import org.lushlife.negroni.core.configuration.LCollections;
import org.lushlife.negroni.core.impl.delegate.DelegateMethod;
import org.lushlife.negroni.core.impl.inject.Injections;



public abstract class ConfiguratorBase implements Configurator {

	public void init() {
		configuration(LCollections.class);
	}

	ConcurrentHashMap<Class<?>, List<DelegateMethod>> cash = new ConcurrentHashMap<Class<?>, List<DelegateMethod>>();

	public Map<Class<?>, List<DelegateMethod>> getCash() {
		return cash;
	}

	ConcurrentHashMap<String, Identifier<?>> nameMapping = new ConcurrentHashMap<String, Identifier<?>>();

	public Map<String, Identifier<?>> getNameMapping() {
		return nameMapping;
	}

	public Identifier<?> toId(Type type) {
		return toId(type, null);
	}

	public <T> Configurator configuration(Class<? extends Enum<?>> configuration) {
		for (Enum<?> e : configuration.getEnumConstants()) {
			configuration(e);
		}
		return this;
	}

	public <T> Configurator configuration(Enum<?> configuration) {
		final Configuration binding = new Configuration(configuration, this);
		final Identifier<?> id = Injections.toId(this, configuration, binding);
		if (binding.named()) {
			getNameMapping().put(configuration.name(), id);
		}
		configuration(binding, id);
		return this;
	}

	protected abstract void configuration(Configuration configuration,
			Identifier<?> id);
}
