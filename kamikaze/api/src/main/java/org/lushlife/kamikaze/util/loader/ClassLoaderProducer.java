package org.lushlife.kamikaze.util.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;

import org.lushlife.kamikaze.LogMsgCore;
import org.lushlife.kamikaze.context.Contexts;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class ClassLoaderProducer {

	static public void produceClassLoader(ServletContext context) {
		URL url;
		try {
			url = context.getResource("/WEB-INF/modules/");
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
		if (url != null) {
			produceClassLoader(context, url);
		}
	}

	static Log logger = Logging.getLog(ClassLoaderProducer.class);
	public static final String LAST_UPDATE_TIME = "LAST_UPDATE_TIME";

	private static void produceClassLoader(ServletContext context, URL... urls) {

		logger.log(LogMsgCore.KMKZ00005, Arrays.toString(urls));

		ClassLoader loader = new URLClassLoader(urls, Thread.currentThread()
				.getContextClassLoader());
		context.setAttribute(ClassLoaderProducer.class.getName(), loader);
		context.setAttribute(LAST_UPDATE_TIME, System.currentTimeMillis());
	}

	@Produces
	static public ClassLoader getClassLoader() {
		ServletContext context = Contexts.getServletContext();
		if (context == null) {
			return Thread.currentThread().getContextClassLoader();
		}
		return getClassLoader(context);
	}

	static public ClassLoader getClassLoader(ServletContext context) {
		ClassLoader loader = (ClassLoader) context
				.getAttribute(ClassLoaderProducer.class.getName());
		if (loader == null) {
			return Thread.currentThread().getContextClassLoader();
		}
		return loader;
	}

}
