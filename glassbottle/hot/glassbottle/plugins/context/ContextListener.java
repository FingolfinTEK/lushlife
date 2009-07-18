package glassbottle.plugins.context;


import glassbottle.core.binding.RequestDestroyed;
import glassbottle.core.binding.RequestInitialized;
import glassbottle.core.scope.Singleton;
import glassbottle.core.scope.SingletonContext;
import glassbottle.plugins.context.scope.EventContext;
import glassbottle.plugins.context.scope.HiddenContext;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Current;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.servlet.ServletRequestEvent;


import org.jboss.webbeans.BeanManagerImpl;
import org.jboss.webbeans.context.api.helpers.ConcurrentHashMapBeanStore;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;


@Singleton
public class ContextListener
{

   Log log = Logging.getLog(ContextListener.class);

   @Current
   BeanManagerImpl manager;

   SingletonContext context = new SingletonContext();

   public void initScope(@Observes AfterDeploymentValidation event)
   {
      log.info("init context ");
      manager.addContext(EventContext.INSTANCE);
      manager.addContext(HiddenContext.INSTANCE);
      manager.addContext(context);
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
