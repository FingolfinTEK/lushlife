package org.lushlife.negroni.context;

import java.util.concurrent.ConcurrentHashMap;

public class InstanceContexts {
	final public static ThreadLocal<ConcurrentHashMap<Object, Object>> instanceScope = new ThreadLocal<ConcurrentHashMap<Object, Object>>();

}
