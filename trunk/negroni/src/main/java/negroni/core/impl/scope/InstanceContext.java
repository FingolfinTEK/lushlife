package negroni.core.impl.scope;

import java.util.concurrent.ConcurrentHashMap;

public class InstanceContext {

	final ConcurrentHashMap<Object, Object> InstanceContext = new ConcurrentHashMap<Object, Object>();

	final Object owner;

	public InstanceContext(Object owner) {
		this.owner = owner;
	}

	public Object getOwner() {
		return owner;
	}

	public ConcurrentHashMap<Object, Object> getContext() {
		return InstanceContext;
	}

}
