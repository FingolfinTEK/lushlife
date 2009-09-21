package org.lushlife.kamikaze.jboss.bootstrap;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.webbeans.bootstrap.api.ServiceRegistry;
import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.ejb.spi.EjbDescriptor;
import org.lushlife.kamikaze.WebBeansBinder;
import org.lushlife.kamikaze.WebBeansModule;
import org.lushlife.kamikaze.jboss.LogMsgJBoss;
import org.lushlife.kamikaze.util.loader.ServiceLoader;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

public class BeanModuleBeanDeploymentArchive implements BeanDeploymentArchive {
	static Log log = Logging.getLog(BeanModuleBeanDeploymentArchive.class);

	static public class BeanBinderImpl implements WebBeansBinder {
		private Set<Class<?>> classes = new HashSet<Class<?>>();
		private Set<URL> xmls = new HashSet<URL>();

		public Set<Class<?>> getClasses() {
			return classes;
		}

		public Set<URL> getXmls() {
			return xmls;
		}

		public void model(Class<?> clazz) {
			log.log(LogMsgJBoss.KMKZJ0004, clazz);
			classes.add(clazz);
		}

		public void install(WebBeansModule module) {
			module.configure(this);
		}

		public void beansXml(URL url) {
			xmls.add(url);
		}

		public void installService(Class<? extends WebBeansModule> clazz) {
			for (WebBeansModule module : ServiceLoader.load(clazz)) {
				install(module);
			}
		}
	}

	final private BeanBinderImpl binder = new BeanBinderImpl();
	final private ServiceRegistry registry;

	public BeanModuleBeanDeploymentArchive(ServiceRegistry registry,
			Iterable<WebBeansModule> modules) {
		this.registry = registry;
		for (WebBeansModule module : modules) {
			module.configure(binder);
		}
	}

	public Collection<Class<?>> getBeanClasses() {
		return binder.getClasses();
	}

	public List<BeanDeploymentArchive> getBeanDeploymentArchives() {
		return new ArrayList<BeanDeploymentArchive>();
	}

	public Collection<URL> getBeansXml() {
		return binder.getXmls();
	}

	public Collection<EjbDescriptor<?>> getEjbs() {
		return new ArrayList<EjbDescriptor<?>>();
	}

	public ServiceRegistry getServices() {
		return registry;
	}

}
