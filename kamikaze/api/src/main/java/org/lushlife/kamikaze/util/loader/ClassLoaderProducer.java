package org.lushlife.kamikaze.util.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.enterprise.inject.Produces;

import org.lushlife.kamikaze.LogMsgKMKZC;
import org.lushlife.kamikaze.context.Contexts;
import org.lushlife.kamikaze.context.SingletonContext;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class ClassLoaderProducer {

	static public void produceClassLoader(SingletonContext<?> context) {
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

	private static void produceClassLoader(SingletonContext<?> context,
			URL... urls) {

		logger.log(LogMsgKMKZC.KMKZC0005, Arrays.toString(urls));

		ClassLoader loader = new URLClassLoader(urls, Thread.currentThread()
				.getContextClassLoader());
		context.set(ClassLoaderProducer.class.getName(), loader);
		context.set(LAST_UPDATE_TIME, System.currentTimeMillis());
	}

	@Produces
	static public ClassLoader getClassLoader() {
		SingletonContext<?> context = Contexts.getSingletonContext();
		if (context == null) {
			return Thread.currentThread().getContextClassLoader();
		}
		return getClassLoader(context);
	}

	static public ClassLoader getClassLoader(SingletonContext<?> context) {
		ClassLoader loader = (ClassLoader) context
				.get(ClassLoaderProducer.class.getName());
		if (loader == null) {
			return Thread.currentThread().getContextClassLoader();
		}
		return loader;
	}

	public static void produceClassLoader() {
		produceClassLoader(Contexts.getSingletonContext());
	}
}
