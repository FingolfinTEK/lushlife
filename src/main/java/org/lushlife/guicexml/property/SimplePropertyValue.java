package org.lushlife.guicexml.property;

import java.lang.reflect.Type;

import org.lushlife.guicexml.converter.Conversions;
import org.lushlife.guicexml.el.Expressions;

public class SimplePropertyValue implements PropertyValue {
	private String value;

	@Override
	public Object resolveString(Type type, Expressions expressions) {
		return Conversions.getConverter(type).convert(value);
	}

	public SimplePropertyValue(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimplePropertyValue other = (SimplePropertyValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}