package org.lushlife.guicexml;

import java.lang.reflect.Type;

import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.property.PropertyValue;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.name.Names;

public class Factory<T> {
	class FactoryProvider implements Provider<T> {
		@Inject
		Expressions expressions;

		@SuppressWarnings("unchecked")
		@Override
		public T get() {
			return (T) value.resolveString(type, expressions);
		}
	}

	private Type type;
	private String name;
	private PropertyValue value;

	public Factory(Type type, String name, PropertyValue value) {
		assert value != null;

		this.type = (type != null) ? type : Object.class;
		this.value = value;
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public void bind(Binder binder) {
		Key key = (name != null) ? Key.get(type, Names.named(name)) : Key
				.get(type);
		binder.bind(key).toProvider(new FactoryProvider());
	}
}
