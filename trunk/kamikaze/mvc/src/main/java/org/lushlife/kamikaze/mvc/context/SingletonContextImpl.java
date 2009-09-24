package org.lushlife.kamikaze.mvc.context;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.lushlife.kamikaze.context.SingletonContext;

public class SingletonContextImpl implements SingletonContext<ServletContext> {

	private ServletContext servletContext;

	public Object get(String name) {
		return servletContext.getAttribute(name);
	}

	public ServletContext getDelegate() {
		return servletContext;
	}

	public void set(String name, Object obj) {
		servletContext.setAttribute(name, obj);
	}

	public SingletonContextImpl(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public URL getResource(String path) throws MalformedURLException {
		return servletContext.getResource(path);
	}

	public String getServerInfo() {
		return servletContext.getServerInfo();
	}

}
