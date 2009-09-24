package org.lushlife.negroni.context;

import java.util.concurrent.ConcurrentHashMap;

public class InstanceContext {

	final ConcurrentHashMap<Object, Object> InstanceContext = new ConcurrentHashMap<Object, Object>();

	final Class<?> owner;

	public InstanceContext(Class<?> owner) {
		this.owner = owner;
	}

	public Object getOwner() {
		return owner;
	}

	public ConcurrentHashMap<Object, Object> getContext() {
		return InstanceContext;
	}

}
