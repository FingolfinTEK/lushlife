package org.lushlife.inject.dsl;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Deployment;

public class DSLDeployment implements Deployment {
	private final DSLBeanDeploymentArchive dslDeploymentArchive;
	private final Collection<BeanDeploymentArchive> beanDeploymentArchives;
	private final ServiceRegistry services;

	public DSLDeployment(Module module) {
		this.dslDeploymentArchive = new DSLBeanDeploymentArchive(module);
		this.beanDeploymentArchives = new ArrayList<BeanDeploymentArchive>();
		this.beanDeploymentArchives.add(dslDeploymentArchive);
		this.services = new SimpleServiceRegistry();
	}

	public Collection<BeanDeploymentArchive> getBeanDeploymentArchives() {
		return beanDeploymentArchives;
	}

	public ServiceRegistry getServices() {
		return services;
	}

	public BeanDeploymentArchive loadBeanDeploymentArchive(Class<?> beanClass) {
		return dslDeploymentArchive;
	}

	public DSLBeanDeploymentArchive getBeanDeploymentArchive() {
		return dslDeploymentArchive;
	}

}
