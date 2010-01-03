package org.lushlife.inject.dsl;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.ejb.spi.EjbDescriptor;

public class DSLBeanDeploymentArchive implements BeanDeploymentArchive {
	final HashSet<Class<?>> classes = new HashSet<Class<?>>();
	final HashSet<URL> urls = new HashSet<URL>();
	final ServiceRegistry serviceRegistry = new SimpleServiceRegistry();

	public DSLBeanDeploymentArchive(Module module) {
		module.configutaion(new Configutaion() {

			public void include(Module module) {
				module.configutaion(this);
			}

			public void bean(Class<?> clazz) {
				classes.add(clazz);
			}

			public void beansXml(URL url) {
				urls.add(url);
			}
		});
	}

	public Collection<Class<?>> getBeanClasses() {
		return classes;
	}

	public Collection<BeanDeploymentArchive> getBeanDeploymentArchives() {
		return Collections.emptyList();
	}

	public Collection<URL> getBeansXml() {
		return urls;
	}

	public Collection<EjbDescriptor<?>> getEjbs() {
		return Collections.emptyList();
	}

	public String getId() {
		return "DSL";
	}

	public ServiceRegistry getServices() {
		return serviceRegistry;
	}

}
