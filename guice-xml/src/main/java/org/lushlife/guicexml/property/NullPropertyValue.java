package org.lushlife.guicexml.property;

import java.lang.reflect.Type;

import org.lushlife.guicexml.el.Expressions;

public class NullPropertyValue implements PropertyValue {
	// for trace
	transient private Object createor;

	public NullPropertyValue(Object createor) {
		this.createor = createor;
	}

	@Override
	public Object resolveString(Type type, Expressions expressions) {
		return null;
	}

	public String toString() {
		return "NullPropertyValue( created ty " + createor.getClass() + ")";
	}
}
