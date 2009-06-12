package lushfile.core.context;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.servlet.ServletContext;

import lushfile.core.servlet.LushListener;
import lushfile.core.util.HotDeployClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassLoaderManager {

	static public void buildClassLoader(ServletContext context) {
		URL url;
		try {
			url = context.getResource("/WEB-INF/modules/");
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
		if (url != null) {
			buildClassLoader(context, url);
		}
	}

	static Logger logger = LoggerFactory.getLogger(ClassLoaderManager.class);

	private static void buildClassLoader(ServletContext context, URL... urls) {
		if (logger.isInfoEnabled()) {
			logger.info("hot deploy classloader create  "
					+ Arrays.toString(urls));
		}
		ClassLoader loader = new HotDeployClassLoader(urls, Thread
				.currentThread().getContextClassLoader());
		context.setAttribute(ClassLoaderManager.class.getName(), loader);
		context.setAttribute(LushListener.LAST_UPDATE_TIME, System
				.currentTimeMillis());
	}

	static public ClassLoader getClassLoader() {
		return getClassLoader(LushContext.getServletContext());
	}

	static public ClassLoader getClassLoader(ServletContext context) {
		ClassLoader loader = (ClassLoader) context
				.getAttribute(ClassLoaderManager.class.getName());
		if (loader == null) {
			return Thread.currentThread().getContextClassLoader();
		}
		return loader;
	}

}
