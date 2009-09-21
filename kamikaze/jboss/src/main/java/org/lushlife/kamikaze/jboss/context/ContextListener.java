package org.lushlife.kamikaze.jboss.context;

import java.util.UUID;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletRequestEvent;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;
import org.lushlife.kamikaze.event.RequestDestroyed;
import org.lushlife.kamikaze.event.RequestInitialized;
import org.lushlife.kamikaze.jboss.LogMsgJBoss;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;
import org.slf4j.MDC;

@Singleton
public class ContextListener {

	Log log = Logging.getLog(ContextListener.class);

	@Inject
	BeanManagerImpl manager;

	public void initScope(@Observes AfterDeploymentValidation event) {
		UUID id = UUID.randomUUID();
		MDC.put("requestId", id.toString());
		manager.addContext(EventContext.INSTANCE);
		// manager.addContext(HiddenContext.INSTANCE);
	}

	public void requsetInit(
			@Observes @RequestInitialized ServletRequestEvent event) {
		MDC.remove("requestId");
		EventContext.INSTANCE.setBeanStore(new ConcurrentHashMapBeanStore());
		EventContext.INSTANCE.setActive(true);
		// HiddenContext.INSTANCE.setActive(true);
		log.log(LogMsgJBoss.KMKZJ0001);
	}

	public void requestDestory(
			@Observes @RequestDestroyed ServletRequestEvent event) {
		EventContext.INSTANCE.setBeanStore(null);
		EventContext.INSTANCE.setActive(false);
		// HiddenContext.INSTANCE.setActive(false);
		log.log(LogMsgJBoss.KMKZJ0002);
	}

}
