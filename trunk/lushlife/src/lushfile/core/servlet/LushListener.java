package lushfile.core.servlet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import lushfile.core.LushLife;
import lushfile.core.context.ClassLoaderManager;
import lushfile.core.context.LushContext;
import lushfile.core.guice.HiddenScopeManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class LushListener extends GuiceServletContextListener implements
		ServletRequestListener {

	static Logger logger = LoggerFactory.getLogger(LushListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		LushContext.getHiddenScope().clear();
		LushContext.setServletContext(event.getServletContext());
		ClassLoaderManager.buildClassLoader(event.getServletContext());
		try {
			super.contextDestroyed(event);
		} finally {
			LushContext.setServletContext(null);
			LushContext.getHiddenScope().clear();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		LushContext.getHiddenScope().clear();
		LushContext.setServletContext(event.getServletContext());
		ClassLoaderManager.buildClassLoader(event.getServletContext());
		try {
			super.contextInitialized(event);
		} finally {
			LushContext.setServletContext(null);
			LushContext.getHiddenScope().clear();
		}
	}

	@Override
	protected Injector getInjector() {
		return LushLife.getInjector();
	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		LushContext.setServletContext(null);
		LushContext.getHiddenScope().clear();
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		LushContext.getHiddenScope().clear();
		LushContext.setServletContext(event.getServletContext());
		if (LushLife.isHotdeployMode()) {
			if (isUpdate()) {
				ClassLoaderManager.buildClassLoader(event.getServletContext());
				LushLife.resetInjector();
			}
		}
		LushLife.getInjector().getInstance(HiddenScopeManager.class)//
				.restore(event.getServletRequest());
	}

	static public final String LAST_UPDATE_TIME = "LUSH_LAST_UPDATE";

	private boolean isUpdate() {
		ServletContext context = LushContext.getServletContext();
		Long lastUpdate = (Long) context.getAttribute(LAST_UPDATE_TIME);
		if (lastUpdate == null) {
			return false;
		}
		long time = lastUpdate;
		try {
			URL url = context.getResource("/WEB-INF/modules/");
			if (url.toString().startsWith("file")) {
				File file = new File(url.getFile());
				LinkedList<File> files = new LinkedList<File>();
				files.add(file);
				while (!files.isEmpty()) {
					File target = files.pollLast();
					if (time < target.lastModified()) {
						return true;
					}
					if (target.isDirectory()) {
						for (File f : target.listFiles()) {
							files.addLast(f);
						}
					}
				}
			}
			return false;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
