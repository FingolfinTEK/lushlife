package glassbottle2.context;

import glassbottle2.binding.RequestDestroyed;
import glassbottle2.binding.RequestInitialized;
import glassbottle2.context.scope.HiddenContext;
import glassbottle2.scope.EventContext;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletRequestEvent;

import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

@Singleton
public class ContextListener
{

   Log log = Logging.getLog(ContextListener.class);

   @Inject
   BeanManagerImpl manager;

   public void initScope(@Observes AfterDeploymentValidation event)
   {
      log.info("init context ");
      manager.addContext(EventContext.INSTANCE);
      manager.addContext(HiddenContext.INSTANCE);
   }

   public void requsetInit(@Observes @RequestInitialized ServletRequestEvent event)
   {
      EventContext.INSTANCE.setBeanStore(new ConcurrentHashMapBeanStore());
      EventContext.INSTANCE.setActive(true);
      HiddenContext.INSTANCE.setActive(true);
      log.info("request context initialized");
   }

   public void requestDestory(@Observes @RequestDestroyed ServletRequestEvent event)
   {
      EventContext.INSTANCE.setBeanStore(null);
      EventContext.INSTANCE.setActive(false);
      HiddenContext.INSTANCE.setActive(false);
      log.info("request context destoryed ");
   }

}
