package org.lushlife.kamikaze.mvc.jboss.jaxrs;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.lushlife.kamikaze.spi.PostInitializationEvent;

public class JaxRSInitializer {
	private ResteasyBootstrap resteasyBootstrap = new ResteasyBootstrap();

	@Inject
	private ServletContext context;

	public void contextInitialized(@Observes PostInitializationEvent deployment) {
		ServletContextEvent event = new ServletContextEvent(context);
		resteasyBootstrap.contextInitialized(event);
		final ServletContext context = event.getServletContext();
		final Registry registry = (Registry) context
				.getAttribute(Registry.class.getName());
		final ResteasyProviderFactory providerFactory = (ResteasyProviderFactory) context
				.getAttribute(ResteasyProviderFactory.class.getName());
		final ModuleProcessor processor = new ModuleProcessor(registry,
				providerFactory);
		processor.process(deployment.getBeanClasses());
	}

	public void destroy(BeforeShutdown shutdown) {
		resteasyBootstrap.contextDestroyed(new ServletContextEvent(context));
	}

}
