package org.lushlife.persistence;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

@Singleton
public class EntityManagerFactoryProducer {

	static private Log log = Logging.getLog(EntityManagerFactoryProducer.class);

	@Produces
	private EntityManagerFactory factory;

	@PersistenceUnitName
	@Inject
	String persistenceUnitName;

	@PostConstruct
	public void init() {
		log.log(LogPersistence.FACTORY_CREATE);
		this.factory = Persistence
				.createEntityManagerFactory(persistenceUnitName);
	}

	@PreDestroy
	public void destroy() {
		log.log(LogPersistence.FACTORY_DESTROY);
		this.factory.close();
	}

	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}

}
