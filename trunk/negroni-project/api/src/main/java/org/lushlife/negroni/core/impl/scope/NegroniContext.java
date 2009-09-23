package org.lushlife.negroni.core.impl.scope;

import java.util.concurrent.ConcurrentHashMap;

public class NegroniContext {
	final public static ThreadLocal<ConcurrentHashMap<Object, Object>> instanceScope = new ThreadLocal<ConcurrentHashMap<Object, Object>>();

}
