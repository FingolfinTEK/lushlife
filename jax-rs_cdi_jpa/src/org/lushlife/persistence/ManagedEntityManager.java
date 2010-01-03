package org.lushlife.persistence;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.lushlife.stla.Log;

@RequestScoped
public class ManagedEntityManager extends EntityManagerDelegate {
	private static final long serialVersionUID = 1825493837836731529L;

	public ManagedEntityManager() {
		super();
	}

	@Inject
	public ManagedEntityManager(EntityManagerFactoryProducer producer) {
		super(producer.createEntityManager());
	}

	@Inject
	private Log log;

	@PreDestroy
	public void destroy() {
		log.log(LogPersistence.MANAGER_DESTROY);
		this.close();
	}

}
