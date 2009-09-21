package org.lushlife.kamikaze.jboss.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.jboss.webbeans.bootstrap.api.ServiceRegistry;
import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.bootstrap.spi.Deployment;

public class KamikazeDeployment implements Deployment {

	BeanModuleBeanDeploymentArchive disc;

	public KamikazeDeployment(BeanModuleBeanDeploymentArchive disc) {
		this.disc = disc;
	}

	public List<BeanDeploymentArchive> getBeanDeploymentArchives() {
		List<BeanDeploymentArchive> a = new ArrayList<BeanDeploymentArchive>();
		a.add(disc);
		return a;
	}

	public BeanDeploymentArchive loadBeanDeploymentArchive(Class<?> beanClass) {
		return disc;
	}

	public ServiceRegistry getServices() {
		return disc.getServices();
	}

}
