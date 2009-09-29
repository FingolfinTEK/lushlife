package org.lushlife.negroni.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javassist.util.proxy.MethodFilter;

public class AbstractMethodFilter implements MethodFilter {

	public boolean isHandled(Method m) {
		return Modifier.isAbstract(m.getModifiers());
	}

}
