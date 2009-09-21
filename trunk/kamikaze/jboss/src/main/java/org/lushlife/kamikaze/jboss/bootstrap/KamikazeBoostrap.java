package org.lushlife.kamikaze.jboss.bootstrap;

import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContext;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.bootstrap.WebBeansBootstrap;
import org.jboss.webbeans.bootstrap.api.Bootstrap;
import org.jboss.webbeans.bootstrap.api.Environments;
import org.jboss.webbeans.bootstrap.api.ServiceRegistry;
import org.jboss.webbeans.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;
import org.jboss.webbeans.servlet.api.ServletServices;
import org.lushlife.kamikaze.BeanModule;
import org.lushlife.kamikaze.KamikazeContext;
import org.lushlife.kamikaze.jboss.context.EventContext;
import org.lushlife.kamikaze.jboss.context.SingletonContext;
import org.lushlife.kamikaze.servlet.BootstrapService;
import org.lushlife.kamikaze.spi.PostDeployEvent;
import org.lushlife.kamikaze.util.loader.ClassLoaderProducer;

public class KamikazeBoostrap implements BootstrapService {
	static public Log logger = Logging.getLog(KamikazeBoostrap.class);

	public void initManager() {
		Iterable<BeanModule> modules = ModuleLoader
				.loadModules(ClassLoaderProducer.getClassLoader());
		if (logger.isDebugEnabled()) {
			for (BeanModule module : modules) {
				logger.debug("load bean module [{0}]", module.getClass());
			}
		}
		initManager(modules);
	}

	public void initManager(BeanModule... module) {
		initManager(Arrays.asList(module));
	}

	public void initManager(Iterable<BeanModule> module) {
		destoryManager();
		final SimpleServiceRegistry service = new SimpleServiceRegistry();
		final BeanModuleBeanDeploymentArchive archive = new BeanModuleBeanDeploymentArchive(
				service, module);
		Bootstrap bootstrap = new WebBeansBootstrap() {

			@Override
			protected void initializeContexts() {
				super.initializeContexts();
			}

			@Override
			protected void createContexts() {
				super.createContexts();
			}

		};

		final KamikazeDeployment deployment = new KamikazeDeployment(archive);
		service.add(ServletServices.class, new ServletServices() {

			public void cleanup() {
			}

			public BeanDeploymentArchive getBeanDeploymentArchive(
					ServletContext ctx) {
				return archive;
			}
		});
		bootstrap.startContainer(Environments.SERVLET, deployment,
				new ConcurrentHashMapBeanStore());
		bootstrap.startInitialization().deployBeans().validateBeans()
				.endInitialization();

		// GlassBottle.setBootstrap(bootstrap);
		KamikazeContext.setRegistries(Bootstrap.class, bootstrap);

		KamikazeContext.setRegistries(ServiceRegistry.class, service);
		BeanManagerImpl manager = (BeanManagerImpl) bootstrap
				.getManager(archive);

		KamikazeContext.setRegistries(BeanManager.class, manager);
		manager.addContext(new SingletonContext());
		manager.addContext(EventContext.INSTANCE);
		manager.fireEvent(new PostDeployEvent() {
			public Collection<Class<?>> getClasses() {
				return deployment.getBeanDeploymentArchives().get(0)
						.getBeanClasses();
			}
		});
	}

	public void destoryManager() {
		Bootstrap bootStrap = KamikazeContext.get(Bootstrap.class);
		if (bootStrap != null) {
			bootStrap.shutdown();
		}
	}
}
