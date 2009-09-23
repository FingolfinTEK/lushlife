package negroni.core.impl.inject;

import java.lang.reflect.Field;

import negroni.core.impl.reflection.Reflections;


public class FiledInjection<T> implements Injection<T> {
	Field field;

	public FiledInjection(Field field) {
		this.field = field;
		field.setAccessible(true);
	}

	public String getName() {
		return field.getName();
	}

	public void setValue(Object owner, T value) {

		Reflections.set(owner, field, value);
	}

	public String toString() {
		return field.toString();
	}

}
