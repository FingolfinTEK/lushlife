package org.lushlife.kamikaze.jboss.bootstrap;

import java.util.Collection;

import javax.enterprise.inject.spi.BeanManager;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.bootstrap.WebBeansBootstrap;
import org.jboss.webbeans.bootstrap.api.Bootstrap;
import org.jboss.webbeans.bootstrap.api.Environments;
import org.jboss.webbeans.bootstrap.api.ServiceRegistry;
import org.jboss.webbeans.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.context.Contexts;
import org.lushlife.kamikaze.jboss.LogMsgKMKZJ;
import org.lushlife.kamikaze.jboss.context.EventContext;
import org.lushlife.kamikaze.jboss.context.SingletonContext;
import org.lushlife.kamikaze.spi.BootstrapService;
import org.lushlife.kamikaze.spi.PostInitializationEvent;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class KamikazeBoostrap implements BootstrapService {
	static public Log logger = Logging.getLog(KamikazeBoostrap.class);

	public void bootManager(Iterable<WebBeansModule> modules) {
		if (logger.isEnableFor(LogMsgKMKZJ.KMKZJ0003)) {
			for (WebBeansModule module : modules) {
				logger.log(LogMsgKMKZJ.KMKZJ0003, module.getClass());
			}
		}
		shutdownManager();
		final SimpleServiceRegistry service = new SimpleServiceRegistry();
		final BeanModuleBeanDeploymentArchive archive = new BeanModuleBeanDeploymentArchive(
				service, modules);
		Bootstrap bootstrap = new WebBeansBootstrap();

		final KamikazeDeployment deployment = initBeanDeployer(service,
				archive, bootstrap);
		bootstrap.startInitialization().deployBeans().validateBeans()
				.endInitialization();

		Contexts.setRegistries(Bootstrap.class, bootstrap);

		Contexts.setRegistries(ServiceRegistry.class, service);
		BeanManagerImpl manager = (BeanManagerImpl) bootstrap
				.getManager(archive);

		Contexts.setRegistries(BeanManager.class, manager);
		manager.addContext(new SingletonContext());
		manager.addContext(EventContext.INSTANCE);
		manager.fireEvent(new PostInitializationEvent() {
			public Collection<Class<?>> getBeanClasses() {
				return deployment.getBeanDeploymentArchives().get(0)
						.getBeanClasses();
			}
		});
	}

	private KamikazeDeployment initBeanDeployer(
			final SimpleServiceRegistry service,
			final BeanModuleBeanDeploymentArchive archive, Bootstrap bootstrap) {
		final KamikazeDeployment deployment = new KamikazeDeployment(archive);
//		service.add(ServletServices.class, new ServletServices() {
//
//			public void cleanup() {
//			}
//
//			public BeanDeploymentArchive getBeanDeploymentArchive(
//					ServletContext ctx) {
//				return archive;
//			}
//		});
//		bootstrap.startContainer(Environments.SERVLET, deployment,
//				new ConcurrentHashMapBeanStore());

		bootstrap.startContainer(Environments.SE, deployment,
				new ConcurrentHashMapBeanStore());
		return deployment;
	}

	public void shutdownManager() {
		Bootstrap bootStrap = Contexts.get(Bootstrap.class);
		if (bootStrap != null) {
			bootStrap.shutdown();
		}
	}
}
