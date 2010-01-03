package org.lushlife.persistence;

import org.lushlife.stla.Info;

public enum LogPersistence {
	@Info("EntityManagerFactory create")
	FACTORY_CREATE,

	@Info("EntityManagerFactory destroy")
	FACTORY_DESTROY,

	@Info("EntityManager create")
	MANAGER_CREATE,

	@Info("EntityManager destroy")
	MANAGER_DESTROY

}
