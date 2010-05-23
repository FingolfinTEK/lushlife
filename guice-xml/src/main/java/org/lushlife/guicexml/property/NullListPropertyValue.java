package org.lushlife.guicexml.property;

import java.lang.reflect.Type;

import org.lushlife.guicexml.el.Expressions;

public class NullListPropertyValue extends ListPropertyValue {
	Object creator;

	public NullListPropertyValue(Object creator) {
		super(null);
		this.creator = creator;
	}

	@Override
	public Object resolveString(Type type, Expressions expressions) {
		return null;
	}

	public String toString() {
		return "NullPropertyValue( created ty " + creator.getClass() + ")";
	}
}
