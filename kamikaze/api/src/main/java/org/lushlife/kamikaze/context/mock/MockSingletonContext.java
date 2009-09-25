package org.lushlife.kamikaze.context.mock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lushlife.kamikaze.context.SingletonContext;
import org.lushlife.kamikaze.util.loader.ClassLoaderProducer;

public class MockSingletonContext implements SingletonContext<Object> {
	Map<String, Object> attribute = new ConcurrentHashMap<String, Object>();

	public URL getResource(String string) throws MalformedURLException {
		return ClassLoaderProducer.getClassLoader().getResource(string);
	}

	public String getServerInfo() {
		return "mock mode";
	}

	public Object get(String name) {
		return attribute.get(name);
	}

	public Object getDelegate() {
		return this;
	}

	public void set(String name, Object obj) {
		attribute.put(name, obj);
	}

}
