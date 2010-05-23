package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import javax.management.ObjectName;

import org.lushlife.guicexml.spi.Converter;

class ObjectNameConverter implements Converter<ObjectName> {

	@Override
	public ObjectName convert(String name) {
		if (name == null) {
			return null;
		}
		try {
			return new ObjectName(name);
		} catch (Exception e) {
			throw new IllegalArgumentException("illegal name " + name, e);
		}
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { ObjectName.class };
	}

}
