package org.lushlife.guicexml.property;

import java.lang.reflect.Type;

import org.lushlife.guicexml.el.Expressions;

public interface PropertyValue {

	Object resolveString(Type type, Expressions expressions);

}
