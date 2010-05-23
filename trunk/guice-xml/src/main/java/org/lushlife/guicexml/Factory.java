package org.lushlife.guicexml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.property.PropertyValue;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.name.Names;

public class Factory<T> {
	static private Log log = Logging.getLog(Factory.class);

	class FactoryProvider implements Provider<T> {
		Provider<Expressions> expressions;

		public FactoryProvider(Provider<Expressions> expressions) {
			this.expressions = expressions;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T get() {
			return (T) value.resolveString(type, expressions.get());
		}
	}

	private Type type;
	private String name;
	private PropertyValue value;
	Class<? extends Annotation> scope;
	private boolean startup;

	public Factory(Type type, String name, PropertyValue value,
			Class<? extends Annotation> scope, boolean startup) {
		assert value != null;

		this.type = (type != null) ? type : Object.class;
		this.value = value;
		this.name = name;
		this.scope = scope;
		this.startup = startup;
	}

	@Override
	public String toString() {
		return "Factory(name=\"" + name + "\""
				+ ((type != Object.class) ? " type=\"" + type + "\"" : "")
				+ " value=\"" + value + "\")";
	}

	@SuppressWarnings("unchecked")
	public void bind(Binder binder) {
		log.log(GuiceXmlLogMessage.INSTALL_FACTORY, this);
		Provider<Expressions> expression = binder
				.getProvider(Expressions.class);
		Key key = (name != null) ? Key.get(type, Names.named(name)) : Key
				.get(type);
		ScopedBindingBuilder scopedBindingBuilder = binder.bind(key)
				.toProvider(new FactoryProvider(expression));
		if (startup) {
			scopedBindingBuilder.asEagerSingleton();
		} else if (scope != null) {
			scopedBindingBuilder.in(scope);
		}
	}
}
