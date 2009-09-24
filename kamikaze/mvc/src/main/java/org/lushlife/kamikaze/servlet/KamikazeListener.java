package org.lushlife.kamikaze.servlet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.lushlife.kamikaze.Kamikaze;
import org.lushlife.kamikaze.LogMsgCore;
import org.lushlife.kamikaze.context.Contexts;
import org.lushlife.kamikaze.context.ServletContexts;
import org.lushlife.kamikaze.event.RequestDestroyedLiteral;
import org.lushlife.kamikaze.event.RequestInitializedLiteral;
import org.lushlife.kamikaze.spi.BootstrapService;
import org.lushlife.kamikaze.spi.ServletEventService;
import org.lushlife.kamikaze.util.loader.ClassLoaderProducer;
import org.lushlife.kamikaze.util.loader.ServiceLoader;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class KamikazeListener implements ServletRequestListener,
		ServletContextListener {

	private BootstrapService bootstrapService;
	private ServletEventService servletEvent;

	public KamikazeListener() {
		bootstrapService = ServiceLoader.load(BootstrapService.class)
				.getSingle();
		servletEvent = ServiceLoader.load(ServletEventService.class)
				.getSingle();
	}

	static Log logger = Logging.getLog(KamikazeListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		Contexts.getHiddenScope().clear();
		ServletContexts.setServletContext(event.getServletContext());
		ClassLoaderProducer.produceClassLoader();
		try {
			// KamikazeBoostrap.destoryManager();
			bootstrapService.destoryManager();
		} finally {
			Contexts.setServletContext(null);
			Contexts.getHiddenScope().clear();
		}
		// super.contextDestroyed(event);
		servletEvent.contextDestroyed(event);
	}

	public void contextInitialized(ServletContextEvent event) {
		Contexts.getHiddenScope().clear();
		ServletContexts.setServletContext(event.getServletContext());
		ClassLoaderProducer.produceClassLoader();
		try {
			// KamikazeBoostrap.initManager();
			bootstrapService.initManager();

			servletEvent.contextInitialized(event);
			Contexts.get(BeanManager.class).fireEvent(event,
					new RequestInitializedLiteral());
		} finally {
			Contexts.setServletContext(null);
			Contexts.getHiddenScope().clear();
		}
	}

	public void requestDestroyed(ServletRequestEvent event) {
		Contexts.get(BeanManager.class).fireEvent(event,
				new RequestDestroyedLiteral());
		ServletContexts.setServletContext(null);
		ServletContexts.setRequest(null);
		ServletContexts.setResponse(null);
		Contexts.getHiddenScope().clear();
		servletEvent.requestDestroyed(event);

	}

	public void requestInitialized(ServletRequestEvent event) {
		ServletContexts.setServletContext(event.getServletContext());
		ServletContexts.setRequest((HttpServletRequest) event
				.getServletRequest());
		Contexts.getHiddenScope().clear();
		boolean update = isUpdate();
		logger.log(LogMsgCore.KMKZC0003, Kamikaze.isHotdeployMode(), update);

		if (Kamikaze.isHotdeployMode()) {
			if (update) {
				ClassLoaderProducer.produceClassLoader();
			}
			Thread.currentThread().setContextClassLoader(
					ClassLoaderProducer.getClassLoader());
			if (update) {
				logger.log(LogMsgCore.KMKZC0002);
				bootstrapService.initManager();
			}
		}

		Contexts.get(BeanManager.class).fireEvent(event,
				new RequestInitializedLiteral());
		servletEvent.requestInitialized(event);
	}

	private boolean isUpdate() {
		ServletContext context = ServletContexts.getServletContext();
		Long lastUpdate = (Long) context
				.getAttribute(ClassLoaderProducer.LAST_UPDATE_TIME);
		if (lastUpdate == null) {
			return false;
		}
		long time = lastUpdate;
		try {
			URL url = context.getResource("/WEB-INF/modules/");
			if (url == null) {
				return false;
			}
			if (url.toString().startsWith("file")) {
				File file = new File(url.getFile());
				LinkedList<File> files = new LinkedList<File>();
				files.add(file);
				while (!files.isEmpty()) {
					File target = files.removeLast();
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
