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
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;
import org.lushlife.kamikaze.BeanBinder;
import org.lushlife.kamikaze.BeanModule;

public class BeanModuleBeanDeploymentArchive implements BeanDeploymentArchive {
	static Log log = Logging.getLog(BeanModuleBeanDeploymentArchive.class);

	static public class BeanBinderImpl implements BeanBinder {
		private Set<Class<?>> classes = new HashSet<Class<?>>();
		private Set<URL> xmls = new HashSet<URL>();

		public Set<Class<?>> getClasses() {
			return classes;
		}

		public Set<URL> getXmls() {
			return xmls;
		}

		public void model(Class<?> clazz) {
			log.debug("load class {0} ", clazz);
			classes.add(clazz);
		}

		public void install(BeanModule module) {
			module.configure(this);
		}

		public void beansXml(URL url) {
			xmls.add(url);
		}

	}

	final private BeanBinderImpl binder = new BeanBinderImpl();
	final private ServiceRegistry registry;

	public BeanModuleBeanDeploymentArchive(ServiceRegistry registry,
			Iterable<BeanModule> modules) {
		this.registry = registry;
		for (BeanModule module : modules) {
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
